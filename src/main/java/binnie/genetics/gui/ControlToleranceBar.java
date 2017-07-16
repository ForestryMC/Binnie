package binnie.genetics.gui;

import java.util.EnumSet;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Border;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.genetics.Tolerance;

public abstract class ControlToleranceBar<T extends Enum<T>> extends Control implements ITooltip {
	EnumSet<T> tolerated;
	EnumSet<T> fullSet;
	private Class<T> enumClass;

	public ControlToleranceBar(IWidget parent, int x, int y, int width, int height, Class<T> clss) {
		super(parent, x, y, width, height);
		addAttribute(Attribute.MouseOver);
		enumClass = clss;
		tolerated = EnumSet.noneOf(enumClass);
		fullSet = EnumSet.allOf(enumClass);
		if (enumClass == EnumTemperature.class) {
			fullSet.remove(enumClass.cast(EnumTemperature.NONE));
		}
	}

	@Override
	public void getTooltip(Tooltip list) {
		int types = fullSet.size();
		int type = getRelativeMousePosition().x() / (getSize().x() / types);
		for (T tol : fullSet) {
			if (tol.ordinal() - ((enumClass == EnumTemperature.class) ? 1 : 0) == type) {
				list.add((tolerated.contains(tol) ? "" : TextFormatting.DARK_GRAY) + getName(tol));
			}
		}
	}

	protected abstract String getName(T p0);

	protected abstract int getColour(T p0);

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawGradientRect(getArea(), -1431655766, -1431655766);
		int w = getArea().width() / fullSet.size();
		int t = 0;
		for (T value : fullSet) {
			int col = (tolerated.contains(value) ? -16777216 : 855638016) + getColour(value);
			Border inset = new Border(tolerated.contains(value) ? 1 : 3);
			RenderUtil.drawGradientRect(new Area(w * t, 0, w, height()).inset(inset), col, col);
			++t;
		}
	}

	public void setValues(T value, EnumTolerance enumTol) {
		tolerated.clear();
		Tolerance tol = Tolerance.get(enumTol);
		for (T full : fullSet) {
			if (full.ordinal() > value.ordinal() + tol.getBounds()[1]) {
				continue;
			}
			if (full.ordinal() < value.ordinal() + tol.getBounds()[0]) {
				continue;
			}
			tolerated.add(full);
		}
	}
}
