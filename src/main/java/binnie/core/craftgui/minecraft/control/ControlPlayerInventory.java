package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;
import java.util.ArrayList;
import java.util.List;

public class ControlPlayerInventory extends Control {
    private List<ControlSlot> slots;

    public ControlPlayerInventory(IWidget parent, boolean wide) {
        super(
                parent,
                (int) (parent.getSize().x() / 2.0f) - (wide ? 110 : 81),
                (int) parent.getSize().y() - (wide ? 54 : 76) - 12,
                wide ? 220 : 162,
                wide ? 54 : 76);
        slots = new ArrayList<>();
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                ControlSlot slot = new ControlSlot(this, (wide ? 58 : 0) + column * 18, row * 18);
                slots.add(slot);
            }
        }

        if (wide) {
            for (int i1 = 0; i1 < 9; ++i1) {
                ControlSlot slot2 = new ControlSlot(this, i1 % 3 * 18, i1 / 3 * 18);
                slots.add(slot2);
            }
        } else {
            for (int i1 = 0; i1 < 9; ++i1) {
                ControlSlot slot2 = new ControlSlot(this, i1 * 18, 58.0f);
                slots.add(slot2);
            }
        }
        create();
    }

    public ControlPlayerInventory(IWidget parent) {
        this(parent, false);
    }

    public ControlPlayerInventory(IWidget parent, int x, int y) {
        super(parent, x, y, 54.0f, 220.0f);
        slots = new ArrayList<>();
        for (int row = 0; row < 6; ++row) {
            for (int column = 0; column < 6; ++column) {
                ControlSlot slot = new ControlSlot(this, column * 18, row * 18);
                slots.add(slot);
            }
        }
        create();
    }

    public void create() {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                ControlSlot slot = slots.get(column + row * 9);
                slot.assign(InventoryType.Player, 9 + column + row * 9);
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            ControlSlot slot2 = slots.get(27 + i1);
            slot2.assign(InventoryType.Player, i1);
        }
    }

    public ControlSlot getSlot(int i) {
        if (i < 0 || i >= slots.size()) {
            return null;
        }
        return slots.get(i);
    }

    @Override
    public void onUpdateClient() {
        // ignored
    }
}
