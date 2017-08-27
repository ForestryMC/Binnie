package binnie.genetics.gui.analyst;

import java.util.List;

import binnie.core.api.gui.ITitledWidget;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlAnalystButton extends Control {
	private final ITitledWidget value;
	private final WindowAnalyst window;

	public ControlAnalystButton(IWidget parent, int x, int y, int width, int height, WindowAnalyst window, ITitledWidget page) {
		super(parent, x, y, width, height);
		this.window = window;
		addAttribute(Attribute.MOUSE_OVER);
		this.value = page;
		addSelfEventHandler(EventMouse.Down.class, event -> {
			List<ITitledWidget> pages = window.getAnalystPages();
			int currentIndex = pages.indexOf(window.getRightPage().getContent());
			int clickedIndex = pages.indexOf(value);
			if (window.isDatabase()) {
				if (clickedIndex != 0 && clickedIndex != currentIndex) {
					window.setPage(window.getRightPage(), value);
				}
			} else {
				if (clickedIndex < 0) {
					clickedIndex = 0;
				}
				if (clickedIndex < currentIndex) {
					++clickedIndex;
				}
				window.setPage(window.getRightPage(), null);
				window.setPage(window.getLeftPage(), null);
				window.setPage(window.getRightPage(), pages.get(clickedIndex));
				window.setPage(window.getLeftPage(), pages.get(clickedIndex - 1));
			}
		});
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(value.getTitle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		boolean active = value == window.getLeftPage().getContent() || value == window.getRightPage().getContent();
		RenderUtil.setColour((active ? -16777216 : 1140850688) + value.getColor());
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		CraftGUI.RENDER.texture(CraftGUITexture.TAB_SOLID, getArea().inset(1));
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		RenderUtil.setColour(value.getColor());
		CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea().inset(1));
		super.onRenderBackground(guiWidth, guiHeight);
	}
}
