package binnie.core.gui.minecraft.control;

import java.util.ArrayList;
import java.util.List;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.minecraft.InventoryType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ControlPlayerInventory extends Control {
	private final List<ControlSlot> slots;

	public ControlPlayerInventory(final IWidget parent, final boolean wide) {
		super(parent, parent.getSize().xPos() / 2 - (wide ? 110 : 81), parent.getSize().yPos() - (wide ? 54 : 76) - 12, wide ? 220 : 162, wide ? 54 : 76);
		final int firstRowColumn = 9;
		final int rowCount = 3;
		final int columnCount = 9;
		this.slots = new ArrayList<>(firstRowColumn + rowCount*columnCount);
		for (int row = 0; row < rowCount; ++row) {
			for (int column = 0; column < columnCount; ++column) {
				final ControlSlot slot = new ControlSlot.Builder(this, (wide ? 58 : 0) + column * 18, row * 18)
                        .assign(InventoryType.PLAYER, firstRowColumn + column + row * columnCount);
				this.slots.add(slot);
			}
		}
		if (wide) {
			for (int i1 = 0; i1 < firstRowColumn; ++i1) {
				final ControlSlot slot2 = new ControlSlot.Builder(this, i1 % 3 * 18, i1 / 3 * 18).assign(InventoryType.PLAYER, i1);
				this.slots.add(slot2);
			}
		} else {
			for (int i1 = 0; i1 < firstRowColumn; ++i1) {
				final ControlSlot slot2 = new ControlSlot.Builder(this, i1 * 18, 58).assign(InventoryType.PLAYER, i1);
				this.slots.add(slot2);
			}
		}
	}

	public ControlPlayerInventory(final IWidget parent) {
		this(parent, false);
	}

	public ControlPlayerInventory(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 54, 220);
		final int rowCount = 6;
		final int columnCount = 6;
		this.slots = new ArrayList<>();
		for (int row = 0; row < rowCount; ++row) {
			for (int column = 0; column < columnCount; ++column) {
				final ControlSlot slot = new ControlSlot.Builder(this, column * 18, row * 18)
                        .assign(InventoryType.PLAYER,column + row * columnCount);
				this.slots.add(slot);
			}
		}
	}
}
