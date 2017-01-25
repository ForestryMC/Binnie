package binnie.genetics.gui;

import binnie.core.genetics.Tolerance;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import net.minecraft.util.text.TextFormatting;

import java.util.EnumSet;

public abstract class ControlToleranceBar<T extends Enum<T>> extends Control implements ITooltip {
	private Class<T> enumClass;
	EnumSet<T> tolerated;
	EnumSet<T> fullSet;

	public ControlToleranceBar(final IWidget parent, final int x, final int y, final int width, final int height, final Class<T> clss) {
		super(parent, x, y, width, height);
		this.addAttribute(Attribute.MouseOver);
		this.enumClass = clss;
		this.tolerated = EnumSet.noneOf(this.enumClass);
		this.fullSet = EnumSet.allOf(this.enumClass);
		if (this.enumClass == EnumTemperature.class) {
			this.fullSet.remove(this.enumClass.cast(EnumTemperature.NONE));
		}
	}

	@Override
	public void getTooltip(final Tooltip list) {
		final int types = this.fullSet.size();
		final int type = (int) ((int) this.getRelativeMousePosition().x() / (this.getSize().x() / types));
		for (final T tol : this.fullSet) {
			if (tol.ordinal() - ((this.enumClass == EnumTemperature.class) ? 1 : 0) == type) {
				list.add((this.tolerated.contains(tol) ? "" : TextFormatting.DARK_GRAY) + this.getName(tol));
			}
		}
	}

	protected abstract String getName(final T p0);

	protected abstract int getColour(final T p0);

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.gradientRect(this.getArea(), -1431655766, -1431655766);
		final int w = this.getArea().w() / this.fullSet.size();
		int t = 0;
		for (final T value : this.fullSet) {
			final int col = (this.tolerated.contains(value) ? -16777216 : 855638016) + this.getColour(value);
			final IBorder inset = new IBorder(this.tolerated.contains(value) ? 1 : 3);
			CraftGUI.render.gradientRect(new IArea(w * t, 0, w, this.h()).inset(inset), col, col);
			++t;
		}
	}

	public void setValues(final T value, final EnumTolerance enumTol) {
		this.tolerated.clear();
		final Tolerance tol = Tolerance.get(enumTol);
		for (final T full : this.fullSet) {
			if (full.ordinal() > value.ordinal() + tol.getBounds()[1]) {
				continue;
			}
			if (full.ordinal() < value.ordinal() + tol.getBounds()[0]) {
				continue;
			}
			this.tolerated.add(full);
		}
	}
}
