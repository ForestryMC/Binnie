package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.*;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class ControlUser extends Control implements ITooltip {
	private String username;
	String team;

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
