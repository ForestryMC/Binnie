package binnie.core.machines.storage;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.EnumColor;

class ControlColourSelector extends Control implements ITooltip, IControlValue<EnumColor> {
	private EnumColor value;

	public ControlColourSelector(IWidget parent, float x, float y, float w, float h, EnumColor value) {
		super(parent, x, y, w, h);
		setValue(value);
		addAttribute(Attribute.MouseOver);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.add(value.toString());
	}

	@Override
	public EnumColor getValue() {
		return value;
	}

	@Override
	public void setValue(EnumColor value) {
		this.value = value;
		setColour(value.getColour());
	}

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		CraftGUI.Render.gradientRect(getArea(), 0xff000000 + value.getColour(), 0xff000000 + value.getColour());
	}
}
