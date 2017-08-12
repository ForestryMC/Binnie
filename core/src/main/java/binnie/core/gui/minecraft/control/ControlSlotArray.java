package binnie.core.gui.minecraft.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.minecraft.InventoryType;

public class ControlSlotArray extends Control implements Iterable<ControlSlot> {
	private List<ControlSlot> slots = new ArrayList<>();

	private ControlSlotArray(final IWidget parent, final int x, final int y, final int columns, final int rows) {
		super(parent, x, y, columns * 18, rows * 18);
		this.slots = new ArrayList<>();
	}

	public void setItemStacks(final ItemStack[] array) {
		int i = 0;
		for (final ItemStack item : array) {
			if (i >= this.slots.size()) {
				return;
			}
			ControlSlot controlSlot = this.slots.get(i);
			Slot slot = controlSlot.slot;
			slot.putStack(item);
			++i;
		}
	}

	public ControlSlot getControlSlot(final int i) {
		return this.slots.get(i);
	}

	@Override
	public Iterator<ControlSlot> iterator() {
		return this.slots.iterator();
	}

	public static class Builder {
		private final IWidget parent;
		private final int x;
		private final int y;
		private final int columns;
		private final int rows;

		public Builder(final IWidget parent, final int x, final int y, final int columns, final int rows) {
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.columns = columns;
			this.rows = rows;
		}

		public ControlSlotArray create(final int[] index) {
			return this.create(InventoryType.MACHINE, index);
		}

		public ControlSlotArray create(final InventoryType type, final int[] index) {
			ControlSlotArray controlSlots = new ControlSlotArray(parent, x, y, columns, rows);
			int i = 0;
			for (int row = 0; row < rows; ++row) {
				for (int column = 0; column < columns; ++column) {
					ControlSlot.Builder slotBuilder = new ControlSlot.Builder(controlSlots, column * 18, row * 18);
					ControlSlot slot = slotBuilder.assign(type, index[i++]);
					controlSlots.slots.add(slot);
				}
			}

			return controlSlots;
		}
	}
}
