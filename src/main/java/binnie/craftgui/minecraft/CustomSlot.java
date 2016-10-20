// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;

public class CustomSlot extends Slot
{
	@Override
	public boolean isItemValid(final ItemStack par1ItemStack) {
		return this.inventory.isItemValidForSlot(this.getSlotIndex(), par1ItemStack);
	}

	public CustomSlot(final IInventory inventory, final int index) {
		super(inventory, index, 0, 0);
	}

	public InventorySlot getInventorySlot() {
		final IInventorySlots slots = Machine.getInterface(IInventorySlots.class, this.inventory);
		if (slots != null) {
			return slots.getSlot(this.getSlotIndex());
		}
		return null;
	}

	public boolean handleClick() {
		final InventorySlot slot = this.getInventorySlot();
		return slot != null && slot.isRecipe();
	}

	public void onSlotClick(final ContainerCraftGUI container, final int mouseButton, final int modifier, final EntityPlayer player) {
		ItemStack stack = player.inventory.getItemStack();
		if (stack == null || mouseButton == 2) {
			this.putStack((ItemStack) null);
		}
		else {
			stack = stack.copy();
			stack.stackSize = 1;
			this.putStack(stack);
		}
	}
}
