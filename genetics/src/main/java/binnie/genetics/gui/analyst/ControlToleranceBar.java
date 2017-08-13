package binnie.genetics.gui.analyst;

import java.util.EnumSet;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.EnumTemperature;

import binnie.core.genetics.Tolerance;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.renderer.RenderUtil;

public abstract class ControlToleranceBar<T extends Enum<T>> extends Control implements ITooltip {
	EnumSet<T> tolerated;
	EnumSet<T> fullSet;
	private Class<T> enumClass;

	public ControlToleranceBar(IWidget parent, int x, int y, int width, int height, Class<T> clss) {
		super(parent, x, y, width, height);
		addAttribute(Attribute.MOUSE_OVER);
		enumClass = clss;
		tolerated = EnumSet.noneOf(enumClass);
		fullSet = EnumSet.allOf(enumClass);
		if (enumClass == EnumTemperature.class) {
			fullSet.remove(enumClass.cast(EnumTemperature.NONE));
		}
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		int types = fullSet.size();
		int type = getRelativeMousePosition().xPos() / (getSize().xPos() / types);
		for (T tol : fullSet) {
			if (tol.ordinal() - ((enumClass == EnumTemperature.class) ? 1 : 0) == type) {
				tooltip.add((tolerated.contains(tol) ? "" : TextFormatting.DARK_GRAY) + getName(tol));
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
			RenderUtil.drawGradientRect(new Area(w * t, 0, w, getHeight()).inset(inset), col, col);
			++t;
		}
	}

	public void setValues(T value, forestry.api.genetics.EnumTolerance enumTol) {
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
