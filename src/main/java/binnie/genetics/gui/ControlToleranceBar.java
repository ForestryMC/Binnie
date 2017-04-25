// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.genetics.Tolerance;
import forestry.api.genetics.EnumTolerance;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.Tooltip;
import forestry.api.core.EnumTemperature;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.IWidget;
import java.util.EnumSet;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.controls.core.Control;

public abstract class ControlToleranceBar<T extends Enum<T>> extends Control implements ITooltip
{
	private Class<T> enumClass;
	EnumSet<T> tolerated;
	EnumSet<T> fullSet;

	public ControlToleranceBar(final IWidget parent, final float x, final float y, final float width, final float height, final Class<T> clss) {
		super(parent, x, y, width, height);
		this.addAttribute(WidgetAttribute.MouseOver);
		this.enumClass = clss;
		this.tolerated = EnumSet.noneOf(this.enumClass);
		this.fullSet = EnumSet.allOf(this.enumClass);
		if (this.enumClass == EnumTemperature.class) {
			this.fullSet.remove(EnumTemperature.NONE);
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final int types = this.fullSet.size();
		final int type = (int) ((int) this.getRelativeMousePosition().x() / (this.getSize().x() / types));
		for (final T tol : this.fullSet) {
			if (tol.ordinal() - ((this.enumClass == EnumTemperature.class) ? 1 : 0) == type) {
				tooltip.add((this.tolerated.contains(tol) ? "" : "§8") + this.getName(tol));
			}
		}
	}

	protected abstract String getName(final T p0);

	protected abstract int getColour(final T p0);

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.gradientRect(this.getArea(), -1431655766, -1431655766);
		final float w = this.getArea().w() / this.fullSet.size();
		int t = 0;
		for (final T value : this.fullSet) {
			final int col = (this.tolerated.contains(value) ? -16777216 : 855638016) + this.getColour(value);
			final IBorder inset = new IBorder(this.tolerated.contains(value) ? 1.0f : 3.0f);
			CraftGUI.Render.gradientRect(new IArea(w * t, 0.0f, w, this.h()).inset(inset), col, col);
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
