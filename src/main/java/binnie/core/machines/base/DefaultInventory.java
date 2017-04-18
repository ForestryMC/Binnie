package binnie.core.machines.base;

import binnie.core.machines.inventory.IInventoryMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class DefaultInventory implements IInventoryMachine {
	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(final int i, final int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(final int i, final ItemStack itemStack) {
		// ignored
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemStack) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemStack, final int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemStack, final int j) {
		return false;
	}

	@Override
	public boolean isReadOnly(final int slot) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void markDirty() {
		// ignored
	}

	@Override
	public void openInventory() {
		// ignored
	}

	@Override
	public void closeInventory() {
		// ignored
	}
}
