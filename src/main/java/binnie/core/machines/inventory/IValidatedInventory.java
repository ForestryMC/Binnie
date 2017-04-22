package binnie.core.machines.inventory;

import net.minecraft.inventory.*;

interface IValidatedInventory extends IInventory {
	boolean isReadOnly(int slot);
}
