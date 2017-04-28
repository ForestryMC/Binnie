package binnie.botany.craftgui;

import binnie.botany.api.IFlowerColor;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;

public class ControlColorDisplay extends Control implements IControlValue<IFlowerColor>, ITooltip {
	IFlowerColor value;

	public ControlColorDisplay(IWidget parent, float x, float y) {
		super(parent, x, y, 16.0f, 16.0f);
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public IFlowerColor getValue() {
		return value;
	}

	@Override
	public void setValue(IFlowerColor value) {
		this.value = value;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.solid(getArea(), -1);
		CraftGUI.Render.solid(getArea().inset(1), 0xff000000 + getValue().getColor(false));
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		super.getTooltip(tooltip);
		tooltip.add(value.getName());
	}
}
