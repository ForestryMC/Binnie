package binnie.core.machines.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface IInventoryMachine extends IInventory, ISidedInventory, IValidatedInventory {
	IInventory getInventory();

	@Override
	default int getSizeInventory() {
		return this.getInventory().getSizeInventory();
	}

	@Override
	default ItemStack getStackInSlot(final int index) {
		return this.getInventory().getStackInSlot(index);
	}

	@Override
	default ItemStack decrStackSize(final int index, final int amount) {
		return this.getInventory().decrStackSize(index, amount);
	}

	@Override
	default ItemStack removeStackFromSlot(int index) {
		return this.getInventory().removeStackFromSlot(index);
	}

	@Override
	default void setInventorySlotContents(final int index, final ItemStack itemStack) {
		this.getInventory().setInventorySlotContents(index, itemStack);
	}

	@Override
	default boolean isEmpty() {
		return this.getInventory().isEmpty();
	}

	@Override
	default String getName() {
		return this.getInventory().getName();
	}

	@Override
	default int getInventoryStackLimit() {
		return this.getInventory().getInventoryStackLimit();
	}

	@Override
	default void openInventory(EntityPlayer player) {
		this.getInventory().openInventory(player);
	}

	@Override
	default void closeInventory(EntityPlayer player) {
		this.getInventory().closeInventory(player);
	}

	@Override
	default boolean hasCustomName() {
		return this.getInventory().hasCustomName();
	}

	@Override
	default boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
		return this.getInventory().isItemValidForSlot(slot, itemStack);
	}

	@Override
	default int getField(int id) {
		return this.getInventory().getField(id);
	}

	@Override
	default void setField(int id, int value) {
		this.getInventory().setField(id, value);
	}

	@Override
	default int getFieldCount() {
		return this.getInventory().getFieldCount();
	}

	@Override
	default void clear() {
		this.getInventory().clear();
	}
}
