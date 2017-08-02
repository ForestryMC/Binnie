package binnie.genetics.gui.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.scroll.ControlScrollBar;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.gui.window.Panel;

public class AnalystPanel extends Panel {
	WindowAnalyst window;

	public AnalystPanel(WindowAnalyst window) {
		super(window, 16, 54, 280, 164, MinecraftGUI.PanelType.OUTLINE);
		this.window = window;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawGradientRect(getArea(), 1157627903, 1728053247);
		super.onRenderBackground(guiWidth, guiHeight);
	}

	@Override
	public void initialise() {
		setColor(4473924);
		int sectionWidth = (getWidth() - 8 - 4) / 2;
		window.leftPage = new ControlScrollableContent<IWidget>(this, 3, 3, sectionWidth + 2, getHeight() - 8 + 2, 0) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (getContent() == null) {
					return;
				}
				RenderUtil.setColour(getContent().getColor());
				CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea());
			}
		};
		new ControlScrollBar(this, sectionWidth + 2 - 3, 6, 3, getHeight() - 8 + 2 - 6, window.leftPage) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (!isEnabled()) {
					return;
				}
				if (window.leftPage.getContent() == null) {
					return;
				}
				RenderUtil.drawGradientRect(getArea(), 1140850688 + window.leftPage.getContent().getColor(), 1140850688 + window.leftPage.getContent().getColor());
				RenderUtil.drawSolidRect(getRenderArea(), window.leftPage.getContent().getColor());
			}
		};
		window.rightPage = new ControlScrollableContent<IWidget>(this, 3 + sectionWidth + 4, 3, sectionWidth + 2, getHeight() - 8 + 2, 0) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (getContent() == null) {
					return;
				}
				RenderUtil.setColour(getContent().getColor());
				CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea());
			}
		};
		new ControlScrollBar(this, sectionWidth + 2 - 3 + sectionWidth + 4, 6, 3, getHeight() - 8 + 2 - 6, window.rightPage) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (!isEnabled()) {
					return;
				}
				if (window.rightPage.getContent() == null) {
					return;
				}
				RenderUtil.drawGradientRect(getArea(), 1140850688 + window.rightPage.getContent().getColor(), 1140850688 + window.rightPage.getContent().getColor());
				RenderUtil.drawSolidRect(getRenderArea(), window.rightPage.getContent().getColor());
			}
		};
		window.analystPageSize = new Area(1, 1, sectionWidth, getHeight() - 8);
	}
}
