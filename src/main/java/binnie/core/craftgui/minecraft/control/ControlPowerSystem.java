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
import binnie.core.machines.power.PowerSystem;

public class ControlPowerSystem extends Control implements ITooltip
{
	private PowerSystem system;

	public ControlPowerSystem(final IWidget parent, final float x, final float y, final PowerSystem system) {
		super(parent, x, y, 16.0f, 16.0f);
		addAttribute(Attribute.MouseOver);
		this.system = system;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.PowerButton, getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.setType(Tooltip.Type.Power);
		tooltip.add("Power Supply");
		tooltip.add("Powered by " + system.getUnitName());
		tooltip.setMaxWidth(200);
	}
}
