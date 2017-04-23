// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlInfo extends Control implements ITooltip
{
	private String info;

	public ControlInfo(final IWidget parent, final float x, final float y, final String info) {
		super(parent, x, y, 16.0f, 16.0f);
		addAttribute(Attribute.MouseOver);
		this.info = info;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.InfoButton, getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Information);
		tooltip.add("Info");
		tooltip.add(info);
		tooltip.setMaxWidth(200);
	}
}
