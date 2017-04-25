// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.ITooltip;
import binnie.botany.api.IFlowerColour;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.core.Control;

public class ControlColourDisplay extends Control implements IControlValue<IFlowerColour>, ITooltip
{
	IFlowerColour value;

	public ControlColourDisplay(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 16.0f, 16.0f);
		this.addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public IFlowerColour getValue() {
		return this.value;
	}

	@Override
	public void setValue(final IFlowerColour value) {
		this.value = value;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.solid(this.getArea(), -1);
		CraftGUI.Render.solid(this.getArea().inset(1), -16777216 + this.getValue().getColor(false));
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		super.getTooltip(tooltip);
		tooltip.add(this.value.getName());
	}
}
