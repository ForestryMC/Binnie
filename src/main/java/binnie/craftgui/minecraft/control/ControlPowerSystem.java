// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft.control;

import binnie.craftgui.core.Tooltip;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.core.machines.power.PowerSystem;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.controls.core.Control;

public class ControlPowerSystem extends Control implements ITooltip
{
	private PowerSystem system;

	public ControlPowerSystem(final IWidget parent, final float x, final float y, final PowerSystem system) {
		super(parent, x, y, 16.0f, 16.0f);
		this.addAttribute(Attribute.MouseOver);
		this.system = system;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.PowerButton, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Power);
		tooltip.add("Power Supply");
		tooltip.add("Powered by " + this.system.getUnitName());
		tooltip.setMaxWidth(200);
	}
}
