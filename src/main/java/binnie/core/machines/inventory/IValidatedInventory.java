package binnie.core.machines.inventory;

import net.minecraft.inventory.IInventory;

interface IValidatedInventory extends IInventory {
    boolean isReadOnly(int slot);
}
