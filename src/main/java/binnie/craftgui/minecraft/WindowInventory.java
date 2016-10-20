// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;
import java.util.Map;
import net.minecraft.inventory.IInventory;

public class WindowInventory implements IInventory
{
	private Window window;
	private Map<Integer, ItemStack> inventory;
	private Map<Integer, SlotValidator> validators;
	private List<Integer> disabledAutoDispenses;

	public WindowInventory(final Window window) {
		this.inventory = new HashMap<Integer, ItemStack>();
		this.validators = new HashMap<Integer, SlotValidator>();
		this.disabledAutoDispenses = new ArrayList<Integer>();
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
	public ItemStack getStackInSlot(final int var1) {
		if (this.inventory.containsKey(var1)) {
			return this.inventory.get(var1);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(final int index, int amount) {
		if (this.inventory.containsKey(index)) {
			final ItemStack item = this.inventory.get(index);
			final ItemStack output = item.copy();
			final int available = item.stackSize;
			if (amount > available) {
				amount = available;
			}
			final ItemStack itemStack = item;
			itemStack.stackSize -= amount;
			output.stackSize = amount;
			if (item.stackSize == 0) {
				this.setInventorySlotContents(index, null);
			}
			return output;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(final int var1, final ItemStack var2) {
		this.inventory.put(var1, var2);
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "window.inventory";
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
	public boolean isUseableByPlayer(final EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		return !this.validators.containsKey(i) || this.validators.get(i).isValid(itemstack);
	}

	public void createSlot(final int slot) {
		this.inventory.put(slot, null);
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

	public SlotValidator getValidator(final int i) {
		return this.validators.get(i);
	}
}
