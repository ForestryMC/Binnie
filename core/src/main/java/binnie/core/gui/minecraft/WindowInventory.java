package binnie.core.gui.minecraft;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import binnie.core.machines.inventory.SlotValidator;

public class WindowInventory implements IInventory {
	private Window window;
	private Map<Integer, ItemStack> inventory;
	private Map<Integer, SlotValidator> validators;
	private List<Integer> disabledAutoDispenses;

	public WindowInventory(final Window window) {
		this.inventory = new HashMap<>();
		this.validators = new HashMap<>();
		this.disabledAutoDispenses = new ArrayList<>();
		this.window = window;
	}

	@Override
	public int getSizeInventory() {
		if (this.inventory.size() == 0) {
			return 0;
		}
		int max = 0;
		for (final int i : this.inventory.keySet()) {
			if (i > max) {
				max = i;
			}
		}
		return max + 1;
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(final int var1) {
		if (this.inventory.containsKey(var1)) {
			return this.inventory.get(var1);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(final int index, int amount) {
		if (this.inventory.containsKey(index)) {
			final ItemStack item = this.inventory.get(index);
			final ItemStack output = item.copy();
			final int available = item.getCount();
			if (amount > available) {
				amount = available;
			}
			item.shrink(amount);
			output.setCount(amount);
			if (item.isEmpty()) {
				this.setInventorySlotContents(index, ItemStack.EMPTY);
			}
			return output;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack stack) {
		this.inventory.put(index, stack);
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		this.window.onWindowInventoryChanged();
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer var1) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		return !this.validators.containsKey(i) || this.validators.get(i).isValid(itemstack);
	}

	public void createSlot(final int slot) {
		this.inventory.put(slot, ItemStack.EMPTY);
	}

	public void setValidator(final int slot, final SlotValidator validator) {
		this.validators.put(slot, validator);
	}

	public void disableAutoDispense(final int i) {
		this.disabledAutoDispenses.add(i);
	}

	public boolean dispenseOnClose(final int i) {
		return !this.disabledAutoDispenses.contains(i);
	}

	@Nullable
	public SlotValidator getValidator(final int i) {
		return this.validators.get(i);
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public String getName() {
		return "window.inventory";
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return this.inventory.remove(index);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("Inventory");
	}
}
