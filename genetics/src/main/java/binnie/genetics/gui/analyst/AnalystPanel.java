package binnie.genetics.gui.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.scroll.ControlScrollBar;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.gui.window.Panel;

public class AnalystPanel extends Panel {
	public AnalystPanel(WindowAnalyst window) {
		super(window, 16, 54, 280, 164, MinecraftGUI.PanelType.OUTLINE);
		setColor(4473924);
		int sectionWidth = (getWidth() - 8 - 4) / 2;
		window.setLeftPage(new LeftPage(this, sectionWidth));
		new LeftPageScrollBar(this, sectionWidth, window);
		window.setRightPage(new RightPage(this, sectionWidth));
		new RightPageScrollBar(this, sectionWidth, window);
		window.setAnalystPageSize(new Area(1, 1, sectionWidth, getHeight() - 8));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawGradientRect(getArea(), 1157627903, 1728053247);
		super.onRenderBackground(guiWidth, guiHeight);
	}

	private static class LeftPage extends ControlScrollableContent<IWidget> {
		public LeftPage(AnalystPanel analystPanel, int sectionWidth) {
			super(analystPanel, 3, 3, sectionWidth + 2, analystPanel.getHeight() - 8 + 2, 0);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			if (getContent() == null) {
				return;
			}
			RenderUtil.setColour(getContent().getColor());
			CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea());
		}
	}

	private static class LeftPageScrollBar extends ControlScrollBar {
		private final WindowAnalyst window;

		public LeftPageScrollBar(AnalystPanel analystPanel, int sectionWidth, WindowAnalyst window) {
			super(analystPanel, sectionWidth + 2 - 3, 6, 3, analystPanel.getHeight() - 8 + 2 - 6, window.getLeftPage());
			this.window = window;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			if (!isEnabled()) {
				return;
			}
			if (window.getLeftPage().getContent() == null) {
				return;
			}
			RenderUtil.drawGradientRect(getArea(), 1140850688 + window.getLeftPage().getContent().getColor(), 1140850688 + window.getLeftPage().getContent().getColor());
			RenderUtil.drawSolidRect(getRenderArea(), window.getLeftPage().getContent().getColor());
		}
	}

	private static class RightPage extends ControlScrollableContent<IWidget> {
		public RightPage(AnalystPanel analystPanel, int sectionWidth) {
			super(analystPanel, 3 + sectionWidth + 4, 3, sectionWidth + 2, analystPanel.getHeight() - 8 + 2, 0);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			if (getContent() == null) {
				return;
			}
			RenderUtil.setColour(getContent().getColor());
			CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea());
		}
	}

	private static class RightPageScrollBar extends ControlScrollBar {
		private final WindowAnalyst window;

		public RightPageScrollBar(AnalystPanel analystPanel, int sectionWidth, WindowAnalyst window) {
			super(analystPanel, sectionWidth + 2 - 3 + sectionWidth + 4, 6, 3, analystPanel.getHeight() - 8 + 2 - 6, window.getRightPage());
			this.window = window;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			if (!isEnabled()) {
				return;
			}
			if (window.getRightPage().getContent() == null) {
				return;
			}
			RenderUtil.drawGradientRect(getArea(), 1140850688 + window.getRightPage().getContent().getColor(), 1140850688 + window.getRightPage().getContent().getColor());
			RenderUtil.drawSolidRect(getRenderArea(), window.getRightPage().getContent().getColor());
		}
	}
}
