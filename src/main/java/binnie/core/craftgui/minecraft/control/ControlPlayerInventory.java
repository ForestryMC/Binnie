package binnie.core.craftgui.minecraft.control;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;

public class ControlPlayerInventory extends Control {
	private final List<ControlSlot> slots;

	public ControlPlayerInventory(final IWidget parent, final boolean wide) {
		super(parent, parent.getSize().x() / 2 - (wide ? 110 : 81), parent.getSize().y() - (wide ? 54 : 76) - 12, wide ? 220 : 162, wide ? 54 : 76);
		this.slots = new ArrayList<>();
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				final ControlSlot slot = new ControlSlot.Builder(this, (wide ? 58 : 0) + column * 18, row * 18).assign(InventoryType.Player, 9 + column + row * 9);
				this.slots.add(slot);
			}
		}
		if (wide) {
			for (int i1 = 0; i1 < 9; ++i1) {
				final ControlSlot slot2 = new ControlSlot.Builder(this, i1 % 3 * 18, i1 / 3 * 18).assign(InventoryType.Player, i1);
				this.slots.add(slot2);
			}
		} else {
			for (int i1 = 0; i1 < 9; ++i1) {
				final ControlSlot slot2 = new ControlSlot.Builder(this, i1 * 18, 58).assign(InventoryType.Player, i1);
				this.slots.add(slot2);
			}
		}
	}

	public ControlPlayerInventory(final IWidget parent) {
		this(parent, false);
	}

	public ControlPlayerInventory(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 54, 220);
		this.slots = new ArrayList<>();
		for (int row = 0; row < 6; ++row) {
			for (int column = 0; column < 6; ++column) {
				final ControlSlot slot = new ControlSlot.Builder(this, column * 18, row * 18).assign(InventoryType.Player,column + row * 6);
				this.slots.add(slot);
			}
		}
	}

	public void addItem(final ItemStack item) {
		for (final ControlSlot slot : this.slots) {
			if (!slot.slot.getHasStack()) {
				slot.slot.putStack(item);
			}
		}
	}

	public void addInventory(final IInventory inventory) {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack stackInSlot = inventory.getStackInSlot(i);
			this.addItem(stackInSlot);
		}
	}

	@Nullable
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
