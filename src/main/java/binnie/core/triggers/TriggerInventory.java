// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.triggers;

import net.minecraft.inventory.IInventory;

public class TriggerInventory
{
	private static Boolean isSlotEmpty(final IInventory inventory, final int slot) {
		return inventory.getStackInSlot(slot) != null;
	}
}
