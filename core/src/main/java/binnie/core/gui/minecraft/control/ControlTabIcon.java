package binnie.core.gui.minecraft.control;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.genetics.IItemStackRepresentitive;
import binnie.core.gui.controls.tab.ControlTab;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.geometry.Point;

public class ControlTabIcon<T> extends ControlTab<T> {
	private ControlItemDisplay item;

	public ControlTabIcon(final ControlTabBar<T> parent, final int x, final int y, final int w, final int h, final T value) {
		super(parent, x, y, w, h, value);
		this.item = new ControlItemDisplay(this, -8 + w / 2, -8 + h / 2);
		this.item.hastooltip = false;
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
		final int x = parent.getDirection().x();
		this.item.setOffset(new Point((this.isCurrentSelection() || this.isMouseOver()) ? 0 : (-4 * x), 0));
	}

	public boolean hasOutline() {
		return false;
	}

	public int getOutlineColour() {
		return 16777215;
	}
}
