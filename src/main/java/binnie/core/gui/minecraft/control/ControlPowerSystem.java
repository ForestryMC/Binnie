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
import binnie.core.machines.power.PowerSystem;

public class ControlPowerSystem extends Control implements ITooltip {
	private PowerSystem system;

	public ControlPowerSystem(final IWidget parent, final int x, final int y, final PowerSystem system) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MouseOver);
		this.system = system;
	}

	@Override
	@SideOnly(Side.CLIENT)
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
