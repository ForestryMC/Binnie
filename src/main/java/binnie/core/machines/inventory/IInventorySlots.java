package binnie.core.machines.inventory;

import javax.annotation.Nullable;

public interface IInventorySlots {
	InventorySlot addSlot(final int p0, final String p1);

	InventorySlot[] addSlotArray(final int[] p0, final String p1);

	@Nullable
	InventorySlot getSlot(final int index);

	InventorySlot[] getSlots(final int[] p0);

	InventorySlot[] getAllSlots();
}
