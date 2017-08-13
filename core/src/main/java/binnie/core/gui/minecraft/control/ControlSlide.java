package binnie.core.gui.minecraft.control;

import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlSlide extends Control {
	private IArea expanded;
	private IArea shrunk;
	private boolean slideActive;
	private Alignment anchor;
	private String label;

	public ControlSlide(final IWidget parent, final int x, final int y, final int w, final int h, final Alignment anchor2) {
		super(parent, x, y, w, h);
		this.slideActive = true;
		this.label = null;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addAttribute(Attribute.BLOCK_TOOLTIP);
		this.expanded = new Area(this.getPosition(), this.getSize());
		this.anchor = anchor2.opposite();
		final int border = (this.anchor.x() != 0) ? (this.expanded.width() - 6) : (this.expanded.height() - 6);
		this.shrunk = this.expanded.inset(new Border(this.anchor, border));
		this.slideActive = false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (this.label != null) {
			final int lw = RenderUtil.getTextWidth(this.label) + 16;
			final int lh = RenderUtil.getTextHeight() + 16;
			final boolean hor = this.anchor.x() != 0;
			final IArea ar = this.isSlideActive() ? this.expanded : this.shrunk;
			IArea tabArea = new Area(hor ? (-lh / 2) : (-lw / 2), hor ? (-lw / 2) : (-lh / 2), hor ? lh : lw, hor ? lw : lh);
			final Point shift = new Point(ar.width() * (1 - this.anchor.x()) / 2, ar.height() * (1 - this.anchor.y()) / 2);
			tabArea = tabArea.shift(shift.xPos() - (-3 + lh / 2) * this.anchor.x(), shift.yPos() - (-3 + lh / 2) * this.anchor.y());
			ITexture texture = CraftGUI.RENDER.getTexture(this.isSlideActive() ? CraftGUITexture.TAB : CraftGUITexture.TAB_DISABLED).crop(this.anchor.opposite(), 8);
			CraftGUI.RENDER.texture(texture, tabArea);
			texture = CraftGUI.RENDER.getTexture(CraftGUITexture.TAB_OUTLINE).crop(this.anchor.opposite(), 8);
			CraftGUI.RENDER.texture(texture, tabArea.inset(2));
			final Area labelArea = new Area(-lw / 2, 0, lw, lh);
			GlStateManager.pushMatrix();
			GlStateManager.translate(shift.xPos() + this.anchor.x() * 2, shift.yPos() + this.anchor.y() * 2, 0);
			if (this.anchor.x() != 0) {
				GlStateManager.rotate(90.0f, 0.0f, 0.0f, this.anchor.x());
			}
			if (this.anchor.y() > 0) {
				GlStateManager.translate(0.0f, -lh, 0.0f);
			}
			RenderUtil.drawText(labelArea, TextJustification.MIDDLE_CENTER, this.label, 16777215);
			GlStateManager.popMatrix();
		}
		CraftGUI.RENDER.texture(CraftGUITexture.WINDOW, this.getArea());
		final Object slideTexture = (this.anchor == Alignment.BOTTOM) ? CraftGUITexture.SLIDE_DOWN : ((this.anchor == Alignment.TOP) ? CraftGUITexture.SLIDE_UP : ((this.anchor == Alignment.LEFT) ? CraftGUITexture.SLIDE_LEFT : CraftGUITexture.SLIDE_RIGHT));
		CraftGUI.RENDER.texture(slideTexture, new Point((this.anchor.x() + 1) * this.getWidth() / 2 - 8, (this.anchor.y() + 1) * this.getHeight() / 2 - 8));
	}

	public boolean isSlideActive() {
		return this.slideActive;
	}

	@Override
	public void onUpdateClient() {
		final boolean mouseOver = this.isMouseOverWidget(this.getRelativeMousePosition());
		if (mouseOver != this.slideActive) {
			this.setSlide(mouseOver);
		}
	}

	@Override
	public boolean isMouseOverWidget(final IPoint relativeMouse) {
		return this.getArea().outset(this.isSlideActive() ? 16 : 8).outset(new Border(this.anchor.opposite(), 16)).contains(relativeMouse);
	}

	@Override
	public boolean isChildVisible(final IWidget child) {
		return this.slideActive;
	}

	public void setSlide(final boolean b) {
		this.slideActive = b;
		final IArea area = this.isSlideActive() ? this.expanded : this.shrunk;
		this.setSize(area.size());
		this.setPosition(area.pos());
	}

	public void setLabel(final String l) {
		this.label = l;
	}
}
