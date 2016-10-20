package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.InventoryType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ControlPlayerInventory extends Control {
    private List<ControlSlot> slots;

    public ControlPlayerInventory(final IWidget parent, final boolean wide) {
        super(parent, (int) (parent.getSize().x() / 2.0f) - (wide ? 110 : 81), (int) parent.getSize().y() - (wide ? 54 : 76) - 12, wide ? 220 : 162, wide ? 54 : 76);
        this.slots = new ArrayList<ControlSlot>();
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                final ControlSlot slot = new ControlSlot(this, (wide ? 58 : 0) + column * 18, row * 18);
                this.slots.add(slot);
            }
        }
        if (wide) {
            for (int i1 = 0; i1 < 9; ++i1) {
                final ControlSlot slot2 = new ControlSlot(this, i1 % 3 * 18, i1 / 3 * 18);
                this.slots.add(slot2);
            }
        } else {
            for (int i1 = 0; i1 < 9; ++i1) {
                final ControlSlot slot2 = new ControlSlot(this, i1 * 18, 58.0f);
                this.slots.add(slot2);
            }
        }
        this.create();
    }

    public ControlPlayerInventory(final IWidget parent) {
        this(parent, false);
    }

    public ControlPlayerInventory(final IWidget parent, final int x, final int y) {
        super(parent, x, y, 54.0f, 220.0f);
        this.slots = new ArrayList<ControlSlot>();
        for (int row = 0; row < 6; ++row) {
            for (int column = 0; column < 6; ++column) {
                final ControlSlot slot = new ControlSlot(this, column * 18, row * 18);
                this.slots.add(slot);
            }
        }
        this.create();
    }

    public void create() {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                final ControlSlot slot = this.slots.get(column + row * 9);
                slot.assign(InventoryType.Player, 9 + column + row * 9);
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            final ControlSlot slot2 = this.slots.get(27 + i1);
            slot2.assign(InventoryType.Player, i1);
        }
    }

    public void addItem(final ItemStack item) {
        if (item == null) {
            return;
        }
        for (final ControlSlot slot : this.slots) {
            if (!slot.slot.getHasStack()) {
                slot.slot.putStack(item);
            }
        }
    }

    public void addInventory(final IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            this.addItem(inventory.getStackInSlot(i));
        }
    }

    public ControlSlot getSlot(final int i) {
        if (i < 0 || i >= this.slots.size()) {
            return null;
        }
        return this.slots.get(i);
    }

    @Override
    public void onUpdateClient() {
    }
}
