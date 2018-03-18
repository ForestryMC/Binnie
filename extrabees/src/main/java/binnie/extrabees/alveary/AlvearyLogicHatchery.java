package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;

import binnie.extrabees.client.gui.AlvearyContainer;
import binnie.extrabees.client.gui.ContainerHatchery;
import binnie.extrabees.client.gui.GuiContainerAlvearyPart;
import binnie.extrabees.utils.Utils;

public class AlvearyLogicHatchery extends AlvearyLogic {

	private final ItemStackHandler inv;

	public AlvearyLogicHatchery() {
		inv = new LarvaeItemStackHandler();
	}

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		inv.deserializeNBT(nbt.getCompoundTag(INVENTORY_NBT_KEY));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag(INVENTORY_NBT_KEY, inv.serializeNBT());
		return nbt;
	}

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		if (tile.getWorldObj().rand.nextInt(2400) == 0) {
			final IBeeHousing house = tile.getMultiblockLogic().getController();
			if (!house.getErrorLogic().hasErrors()) {
				final ItemStack queenStack = house.getBeeInventory().getQueen();
				IBeeRoot beeRoot = Utils.getBeeRoot();
				final IBee queen = (queenStack.isEmpty()) ? null : beeRoot.getMember(queenStack);
				if (queen != null) {
					ItemStack larvae = beeRoot.getMemberStack(beeRoot.getBee(queen.getGenome()), EnumBeeType.LARVAE);
					for (int i = 0; i < 5; i++) {
						if (inv.insertItem(i, larvae, false).isEmpty()) {
							return;
						}
					}
				}
			}
		}
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerAlvearyPart(getContainer(player, data));
	}

	@Nullable
	@Override
	public AlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerHatchery(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	private static class LarvaeItemStackHandler extends ItemStackHandler {

		public LarvaeItemStackHandler() {
			super(5);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (!stack.isEmpty() && Utils.getBeeRoot().getType(stack) != EnumBeeType.LARVAE) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
	}
}
