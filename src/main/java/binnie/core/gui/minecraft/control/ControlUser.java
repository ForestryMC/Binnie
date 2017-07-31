package binnie.core.gui.minecraft.control;

import java.util.Objects;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlUser extends Control implements ITooltip {
	String team;
	private String username;

	public ControlUser(final IWidget parent, final int x, final int y, final String username) {
		super(parent, x, y, 16, 16);
		this.username = "";
		this.team = "";
		this.addAttribute(Attribute.MouseOver);
		this.username = username;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.UserButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.User);
		tooltip.add("Owner");
		if (!Objects.equals(this.username, "")) {
			tooltip.add(this.username);
		}
		tooltip.setMaxWidth(200);
	}
}
