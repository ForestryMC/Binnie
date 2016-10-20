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

public class ControlInfo extends Control implements ITooltip
{
	private String info;

	public ControlInfo(final IWidget parent, final float x, final float y, final String info) {
		super(parent, x, y, 16.0f, 16.0f);
		this.addAttribute(Attribute.MouseOver);
		this.info = info;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.InfoButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Information);
		tooltip.add("Info");
		tooltip.add(this.info);
		tooltip.setMaxWidth(200);
	}
}
