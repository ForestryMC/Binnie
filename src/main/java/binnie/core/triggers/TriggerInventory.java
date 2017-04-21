package binnie.core.triggers;

import net.minecraft.inventory.IInventory;

// TODO unusing class?
public class TriggerInventory {
	private static Boolean isSlotEmpty(IInventory inventory, int slot) {
		return inventory.getStackInSlot(slot) != null;
	}
}
