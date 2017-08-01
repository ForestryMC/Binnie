package binnie.core.gui.minecraft.control;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlInfo extends Control implements ITooltip {
	private String info;

	public ControlInfo(final IWidget parent, final int x, final int y, final String info) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.info = info;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.INFO_BUTTON, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.INFORMATION);
		tooltip.add("Info");
		tooltip.add(this.info);
		tooltip.setMaxWidth(200);
	}
}
