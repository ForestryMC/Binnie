package binnie.core.craftgui.minecraft;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CustomSlot extends Slot {
    public CustomSlot(IInventory inventory, int index) {
        super(inventory, index, 0, 0);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return inventory.isItemValidForSlot(getSlotIndex(), par1ItemStack);
    }

    public InventorySlot getInventorySlot() {
        IInventorySlots slots = Machine.getInterface(IInventorySlots.class, inventory);
        if (slots != null) {
            return slots.getSlot(getSlotIndex());
        }
        return null;
    }

    public boolean handleClick() {
        InventorySlot slot = getInventorySlot();
        return slot != null && slot.isRecipe();
    }

    public void onSlotClick(ContainerCraftGUI container, int mouseButton, int modifier, EntityPlayer player) {
        ItemStack stack = player.inventory.getItemStack();
        if (stack == null || mouseButton == 2) {
            putStack(null);
        } else {
            stack = stack.copy();
            stack.stackSize = 1;
            putStack(stack);
        }
    }
}
