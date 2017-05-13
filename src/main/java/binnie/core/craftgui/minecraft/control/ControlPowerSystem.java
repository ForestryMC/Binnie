package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.*;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.power.PowerSystem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
