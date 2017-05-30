package binnie.core.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.util.I18N;

public class ControlUser extends Control implements ITooltip {
	protected String team;

	private String username;

	public ControlUser(IWidget parent, float x, float y, String username) {
		super(parent, x, y, 16.0f, 16.0f);
		this.username = username;
		team = "";
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.UserButton, getArea());
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.User);
		tooltip.add(I18N.localise(BinnieCore.instance, "gui.tooltip.owner"));

		if (!username.isEmpty()) {
			tooltip.add(username);
		}
		tooltip.setMaxWidth(200);
	}
}
