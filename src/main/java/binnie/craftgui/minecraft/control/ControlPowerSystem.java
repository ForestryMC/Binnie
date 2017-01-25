package binnie.craftgui.minecraft.control;

import binnie.core.machines.power.PowerSystem;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlPowerSystem extends Control implements ITooltip {
	private PowerSystem system;

	public ControlPowerSystem(final IWidget parent, final int x, final int y, final PowerSystem system) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MouseOver);
		this.system = system;
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.PowerButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Power);
		tooltip.add("Power Supply");
		tooltip.add("Powered by " + this.system.getUnitName());
		tooltip.setMaxWidth(200);
	}
}
