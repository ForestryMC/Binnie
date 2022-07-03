package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControlSlotArray extends Control implements Iterable<ControlSlot> {
    private List<ControlSlot> slots;

    public ControlSlotArray(IWidget parent, int x, int y, int columns, int rows) {
        super(parent, x, y, columns * 18, rows * 18);
        slots = new ArrayList<>();
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                slots.add(createSlot(column * 18, row * 18));
            }
        }
    }

    public ControlSlot createSlot(int x, int y) {
        return new ControlSlot(this, x, y);
    }

    public ControlSlotArray create(int[] index) {
        return create(InventoryType.Machine, index);
    }

    public ControlSlotArray create(InventoryType type, int[] index) {
        int i = 0;
        for (ControlSlot slot : slots) {
            slot.assign(type, index[i++]);
        }
        return this;
    }

    @Override
    public Iterator<ControlSlot> iterator() {
        return slots.iterator();
    }
}
