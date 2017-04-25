// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlHelp extends Control implements ITooltip
{
	public ControlHelp(IWidget parent, float x, float y) {
		super(parent, x, y, 16.0f, 16.0f);
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.HelpButton, getArea());
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Help);
		tooltip.add("Help");
		tooltip.add("To activate help tooltips,");
		tooltip.add("hold down the tab key and");
		tooltip.add("mouse over controls.");
	}

	@Override
	public void getHelpTooltip(Tooltip tooltip) {
		getTooltip(tooltip);
	}
}
