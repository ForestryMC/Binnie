package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.*;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlInfo extends Control implements ITooltip {
	private String info;

	public ControlInfo(final IWidget parent, final int x, final int y, final String info) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MouseOver);
		this.info = info;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.InfoButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Information);
		tooltip.add("Info");
		tooltip.add(this.info);
		tooltip.setMaxWidth(200);
	}
}
