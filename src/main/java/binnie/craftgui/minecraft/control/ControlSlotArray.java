package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.InventoryType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControlSlotArray extends Control implements Iterable<ControlSlot> {
    private int rows;
    private int columns;
    private List<ControlSlot> slots;

    public ControlSlotArray(final IWidget parent, final int x, final int y, final int columns, final int rows) {
        super(parent, x, y, columns * 18, rows * 18);
        this.slots = new ArrayList<ControlSlot>();
        this.rows = rows;
        this.columns = columns;
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                this.slots.add(this.createSlot(column * 18, row * 18));
            }
        }
    }

    public ControlSlot createSlot(final int x, final int y) {
        return new ControlSlot(this, x, y);
    }

    public void setItemStacks(final ItemStack[] array) {
        int i = 0;
        for (final ItemStack item : array) {
            if (i >= this.slots.size()) {
                return;
            }
            this.slots.get(i).slot.putStack(item);
            ++i;
        }
    }

    public ControlSlot getControlSlot(final int i) {
        if (i < 0 || i >= this.slots.size()) {
            return null;
        }
        return this.slots.get(i);
    }

    public ControlSlotArray create(final int[] index) {
        return this.create(InventoryType.Machine, index);
    }

    public ControlSlotArray create(final InventoryType type, final int[] index) {
        int i = 0;
        for (final ControlSlot slot : this.slots) {
            slot.assign(type, index[i++]);
        }
        return this;
    }

    @Override
    public Iterator<ControlSlot> iterator() {
        return this.slots.iterator();
    }
}
