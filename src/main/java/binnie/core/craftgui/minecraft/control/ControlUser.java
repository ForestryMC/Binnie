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

public class ControlUser extends Control implements ITooltip
{
	private String username;
	String team;

	public ControlUser(final IWidget parent, final float x, final float y, final String username) {
		super(parent, x, y, 16.0f, 16.0f);
		this.username = "";
		team = "";
		addAttribute(Attribute.MouseOver);
		this.username = username;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.UserButton, getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.User);
		tooltip.add("Owner");
		if (username != "") {
			tooltip.add(username);
		}
		tooltip.setMaxWidth(200);
	}
}
