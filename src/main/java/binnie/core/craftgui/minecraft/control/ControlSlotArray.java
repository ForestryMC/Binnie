// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControlSlotArray extends Control implements Iterable<ControlSlot>
{
	private int rows;
	private int columns;
	private List<ControlSlot> slots;

	public ControlSlotArray(IWidget parent, int x, int y, int columns, int rows) {
		super(parent, x, y, columns * 18, rows * 18);
		slots = new ArrayList<ControlSlot>();
		this.rows = rows;
		this.columns = columns;
		for (int row = 0; row < rows; ++row) {
			for (int column = 0; column < columns; ++column) {
				slots.add(createSlot(column * 18, row * 18));
			}
		}
	}

	public ControlSlot createSlot(int x, int y) {
		return new ControlSlot(this, x, y);
	}

	public void setItemStacks(ItemStack[] array) {
		int i = 0;
		for (ItemStack item : array) {
			if (i >= slots.size()) {
				return;
			}
			slots.get(i).slot.putStack(item);
			++i;
		}
	}

	public ControlSlot getControlSlot(int i) {
		if (i < 0 || i >= slots.size()) {
			return null;
		}
		return slots.get(i);
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
