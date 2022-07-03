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
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        // ignored
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStack, int direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack itemStack, int direction) {
        return false;
    }

    @Override
    public boolean isReadOnly(int slot) {
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
