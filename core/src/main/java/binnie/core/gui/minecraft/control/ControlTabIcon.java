package binnie.core.gui.minecraft.control;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.genetics.IItemStackRepresentitive;
import binnie.core.gui.controls.tab.ControlTab;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.geometry.Point;

public class ControlTabIcon<T> extends ControlTab<T> {
	private final ControlItemDisplay item;

	public ControlTabIcon(int x, int y, int w, int h, T value) {
		super(x, y, w, h, value);
		this.item = new ControlItemDisplay(this, -8 + w / 2, -8 + h / 2);
		this.item.setHasTooltip(false);
	}

	public ItemStack getItemStack() {
		if (this.value instanceof IItemStackRepresentitive) {
			return ((IItemStackRepresentitive) this.value).getItemStackRepresentitive();
		}
		return ItemStack.EMPTY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		this.item.setItemStack(this.getItemStack());
		ControlTabBar parent = (ControlTabBar) this.getParent();
		int x = parent.getDirection().x();
		boolean selected = this.isCurrentSelection() || this.isMouseOver();
		int xOffset = selected ? 0 : (-4 * x);
		Point offset = new Point(xOffset, 0);
		this.item.setOffset(offset);
	}

	public boolean hasOutline() {
		return false;
	}

	public int getOutlineColour() {
		return 16777215;
	}
}
