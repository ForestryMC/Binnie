// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft.control;

import binnie.craftgui.core.Tooltip;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.controls.core.Control;

public class ControlHelp extends Control implements ITooltip
{
	public ControlHelp(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 16.0f, 16.0f);
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.HelpButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Help);
		tooltip.add("Help");
		tooltip.add("To activate help tooltips,");
		tooltip.add("hold down the tab key and");
		tooltip.add("mouse over controls.");
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		this.getTooltip(tooltip);
	}
}
