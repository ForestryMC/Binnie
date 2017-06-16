package binnie.core.craftgui.minecraft.control;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlHelp extends Control implements ITooltip {
	public ControlHelp(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.HelpButton, this.getArea());
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
