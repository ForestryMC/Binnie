package binnie.genetics.gui.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlSystemButton extends Control {
	BreedingSystem system;
	WindowAnalyst window;

	public ControlSystemButton(int xPos, int yPos, WindowAnalyst window, BreedingSystem system) {
		super(window, xPos, yPos, 20, 20);
		this.system = system;
		this.window = window;
	}

	@Override
	public void initialise() {
		addAttribute(Attribute.MOUSE_OVER);
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			@SideOnly(Side.CLIENT)
			public void onEvent(EventMouse.Down event) {
				window.setSystem(system);
			}
		});
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
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
