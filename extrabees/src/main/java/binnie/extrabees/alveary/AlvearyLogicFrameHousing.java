package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.multiblock.IAlvearyController;
import forestry.api.multiblock.IMultiblockLogicAlveary;

import binnie.extrabees.client.gui.AbstractAlvearyContainer;
import binnie.extrabees.client.gui.ContainerFrameHousing;
import binnie.extrabees.client.gui.GuiContainerAlvearyPart;
import binnie.extrabees.utils.Utils;

public class AlvearyLogicFrameHousing extends AbstractAlvearyLogic {

	private final IItemHandlerModifiable inv;
	private final TileEntityExtraBeesAlvearyPart tile;

	public AlvearyLogicFrameHousing(TileEntityExtraBeesAlvearyPart tile) {
		this.tile = tile;
		inv = new HiveFrameItemStackHandler();
	}

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

	@Override
	public void wearOutEquipment(final int amount) {
		IHiveFrame hiveFrame = this.getHiveFrame();
		if (hiveFrame != null) {
			IBeeRoot beeRoot = Utils.getBeeRoot();
			IMultiblockLogicAlveary multiblockLogic = tile.getMultiblockLogic();
			IAlvearyController alvearyController = multiblockLogic.getController();
			ItemStack queenStack = alvearyController.getBeeInventory().getQueen();
			IBee queen = beeRoot.getMember(queenStack);
			if (queen != null) {
				final int wear = Math.round(amount * 5 * beeRoot.getBeekeepingMode(tile.getWorldObj()).getWearModifier());
				ItemStack frame = inv.getStackInSlot(0);
				ItemStack frameUsed = hiveFrame.frameUsed(alvearyController, frame, queen, wear);
				inv.setStackInSlot(0, frameUsed);
			}
		}
	}

	@Nullable
	private IHiveFrame getHiveFrame() {
		ItemStack stackInSlot = inv.getStackInSlot(0);
		if (!stackInSlot.isEmpty()) {
			return (IHiveFrame) stackInSlot.getItem();
		}
		return null;
	}

	@Override
	public float getTerritoryModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getProductionModifier(genome, currentModifier);
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getFloweringModifier(genome, currentModifier);
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerAlvearyPart(getContainer(player, data));
	}

	@Nullable
	@Override
	public AbstractAlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerFrameHousing(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	private static class HiveFrameItemStackHandler extends ItemStackHandler {

		public HiveFrameItemStackHandler() {
			super(1);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (!(stack.getItem() instanceof IHiveFrame)) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
		}
		return null;
        }
}
