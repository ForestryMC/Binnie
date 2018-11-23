package binnie.core.gui.minecraft.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.minecraft.InventoryType;

@SideOnly(Side.CLIENT)
public class ControlSlotArray extends Control implements Iterable<ControlSlot> {
	private final List<ControlSlot> slots;

	private ControlSlotArray(IWidget parent, int x, int y, int columns, int rows) {
		super(parent, x, y, columns * 18, rows * 18);
		this.slots = new ArrayList<>();
	}

	public void setItemStacks(ItemStack[] array) {
		int i = 0;
		for (ItemStack item : array) {
			if (i >= this.slots.size()) {
				return;
			}
			ControlSlot controlSlot = this.slots.get(i);
			Slot slot = controlSlot.slot;
			slot.putStack(item);
			++i;
		}
	}

	public ControlSlot getControlSlot(int i) {
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

		public Builder(IWidget parent, int x, int y, int columns, int rows) {
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.columns = columns;
			this.rows = rows;
		}

		public ControlSlotArray create(int[] index) {
			return this.create(InventoryType.MACHINE, index);
		}

		public ControlSlotArray create(InventoryType type, int[] index) {
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
