package binnie.genetics.gui.analyst;

import binnie.core.api.genetics.IBreedingSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

@SideOnly(Side.CLIENT)
public class ControlSystemButton extends Control {
	IBreedingSystem system;
	WindowAnalyst window;

	public ControlSystemButton(int xPos, int yPos, WindowAnalyst window, IBreedingSystem system) {
		super(window, xPos, yPos, 20, 20);
		this.system = system;
		this.window = window;

		addAttribute(Attribute.MOUSE_OVER);
		addSelfEventHandler(EventMouse.Down.class, event -> {
			window.setSystem(system);
		});
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(system.getName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.setColour(system.getColour());
		int outset = (window.getSystem() == system) ? 1 : 0;
		CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea().outset(outset));
		if (window.getSystem() == system) {
			RenderUtil.setColour(1140850688 + system.getColour());
			CraftGUI.RENDER.texture(CraftGUITexture.TAB_SOLID, getArea().outset(outset));
		}
		RenderUtil.drawItem(new Point(2, 2), system.getItemStackRepresentitive());
	}
}
