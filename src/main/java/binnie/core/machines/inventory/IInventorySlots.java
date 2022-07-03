package binnie.core.machines.inventory;

public interface IInventorySlots {
    InventorySlot addSlot(int index, String unlocalizedName);

    InventorySlot[] addSlotArray(int[] indexes, String unlocalizedName);

    InventorySlot getSlot(int index);

    InventorySlot[] getSlots(int[] indexes);

    InventorySlot[] getAllSlots();
}
