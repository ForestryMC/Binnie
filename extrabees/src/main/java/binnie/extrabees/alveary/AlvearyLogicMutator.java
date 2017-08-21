package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBeeGenome;

import binnie.extrabees.client.gui.AbstractAlvearyContainer;
import binnie.extrabees.client.gui.ContainerMutator;
import binnie.extrabees.client.gui.GuiContainerMutator;
import binnie.extrabees.utils.AlvearyMutationHandler;

public class AlvearyLogicMutator extends AbstractAlvearyLogic {

	private final IItemHandlerModifiable inv;

	public AlvearyLogicMutator() {
		this.inv = new MutationItemStackHandler();
	}

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

	@Override
	public float getMutationModifier(@Nonnull IBeeGenome genome, @Nonnull IBeeGenome mate, float currentModifier) {
		ItemStack mutator = inv.getStackInSlot(0);
		float mult = AlvearyMutationHandler.getMutationMult(mutator);
		return Math.min(mult * currentModifier, 15f);
	}

	@Override
	public void onQueenDeath() {
		inv.extractItem(0, 1, false);
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerMutator(getContainer(player, data));
	}

	@Nullable
	@Override
	public AbstractAlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerMutator(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	private static class MutationItemStackHandler extends ItemStackHandler {

		public MutationItemStackHandler() {
			super(1);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (!AlvearyMutationHandler.isMutationItem(stack)) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
	}
}
