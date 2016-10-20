package binnie.core.machines.inventory;

public interface IInventorySlots {
    InventorySlot addSlot(final int p0, final String p1);

    InventorySlot[] addSlotArray(final int[] p0, final String p1);

    InventorySlot getSlot(final int p0);

    InventorySlot[] getSlots(final int[] p0);

    InventorySlot[] getAllSlots();
}
