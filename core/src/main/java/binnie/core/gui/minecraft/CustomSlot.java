package binnie.core.gui.minecraft;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;

public class CustomSlot extends Slot {
	public CustomSlot(IInventory inventory, int index) {
		super(inventory, index, 0, 0);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return !stack.isEmpty() && this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
	}

	@Nullable
	public InventorySlot getInventorySlot() {
		IInventorySlots slots = Machine.getInterface(IInventorySlots.class, this.inventory);
		if (slots != null) {
			return slots.getSlot(this.getSlotIndex());
		}
		return null;
	}

	public boolean handleClick() {
		InventorySlot slot = this.getInventorySlot();
		return slot != null && slot.isRecipe();
	}

	public void onSlotClick(ContainerCraftGUI container, int dragType, ClickType modifier, EntityPlayer player) {
		InventoryPlayer inventory = player.inventory;
		ItemStack stack = inventory.getItemStack();
		ItemStack slotStack = getStack().copy();
		//TODO modifier==mouseButton2?
		if (stack.isEmpty() || modifier == ClickType.PICKUP_ALL) {
			this.putStack(ItemStack.EMPTY);
		} else {
			stack = stack.copy();
			stack.setCount(1);
			this.putStack(stack);
		}
	}
}
