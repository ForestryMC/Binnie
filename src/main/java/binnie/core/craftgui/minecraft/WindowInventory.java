package binnie.core.craftgui.minecraft;

import binnie.core.machines.inventory.SlotValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WindowInventory implements IInventory {
    private Window window;
    private Map<Integer, ItemStack> inventory;
    private Map<Integer, SlotValidator> validators;
    private List<Integer> disabledAutoDispenses;

    public WindowInventory(Window window) {
        inventory = new HashMap<>();
        validators = new HashMap<>();
        disabledAutoDispenses = new ArrayList<>();
        this.window = window;
    }

    @Override
    public int getSizeInventory() {
        if (inventory.size() == 0) {
            return 0;
        }

        int max = 0;
        for (int i : inventory.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        return max + 1;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        if (inventory.containsKey(var1)) {
            return inventory.get(var1);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (!inventory.containsKey(index)) {
            return null;
        }

        ItemStack item = inventory.get(index);
        ItemStack output = item.copy();
        int available = item.stackSize;
        if (amount > available) {
            amount = available;
        }

        ItemStack itemStack = item;
        itemStack.stackSize -= amount;
        output.stackSize = amount;
        if (item.stackSize == 0) {
            setInventorySlotContents(index, null);
        }
        return output;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        inventory.put(var1, var2);
        markDirty();
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
        window.onWindowInventoryChanged();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return !validators.containsKey(i) || validators.get(i).isValid(itemstack);
    }

    public void createSlot(int slot) {
        inventory.put(slot, null);
    }

    public void setValidator(int slot, SlotValidator validator) {
        validators.put(slot, validator);
    }

    public void disableAutoDispense(int i) {
        disabledAutoDispenses.add(i);
    }

    public boolean dispenseOnClose(int i) {
        return !disabledAutoDispenses.contains(i);
    }

    public SlotValidator getValidator(int i) {
        return validators.get(i);
    }
}
