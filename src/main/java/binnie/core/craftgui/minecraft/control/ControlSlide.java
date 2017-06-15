package binnie.core.craftgui.minecraft.control;

import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Border;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlSlide extends Control {
	private Area expanded;
	private Area shrunk;
	private boolean slideActive;
	private Position anchor;
	private String label;

	public ControlSlide(final IWidget parent, final int x, final int y, final int w, final int h, final Position anchor2) {
		super(parent, x, y, w, h);
		this.slideActive = true;
		this.label = null;
		this.addAttribute(Attribute.MouseOver);
		this.addAttribute(Attribute.BlockTooltip);
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
			final Area ar = this.isSlideActive() ? this.expanded : this.shrunk;
			Area tabArea = new Area(hor ? (-lh / 2) : (-lw / 2), hor ? (-lw / 2) : (-lh / 2), hor ? lh : lw, hor ? lw : lh);
			final Point shift = new Point(ar.width() * (1 - this.anchor.x()) / 2, ar.height() * (1 - this.anchor.y()) / 2);
			tabArea = tabArea.shift(shift.x() - (-3 + lh / 2) * this.anchor.x(), shift.y() - (-3 + lh / 2) * this.anchor.y());
			Texture texture = CraftGUI.render.getTexture(this.isSlideActive() ? CraftGUITexture.Tab : CraftGUITexture.TabDisabled).crop(this.anchor.opposite(), 8);
			CraftGUI.render.texture(texture, tabArea);
			texture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline).crop(this.anchor.opposite(), 8);
			CraftGUI.render.texture(texture, tabArea.inset(2));
			final Area labelArea = new Area(-lw / 2, 0, lw, lh);
			GlStateManager.pushMatrix();
			GlStateManager.translate(shift.x() + this.anchor.x() * 2, shift.y() + this.anchor.y() * 2, 0);
			if (this.anchor.x() != 0) {
				GlStateManager.rotate(90.0f, 0.0f, 0.0f, this.anchor.x());
			}
			if (this.anchor.y() > 0) {
				GlStateManager.translate(0.0f, -lh, 0.0f);
			}
			RenderUtil.drawText(labelArea, TextJustification.MiddleCenter, this.label, 16777215);
			GlStateManager.popMatrix();
		}
		CraftGUI.render.texture(CraftGUITexture.Window, this.getArea());
		final Object slideTexture = (this.anchor == Position.BOTTOM) ? CraftGUITexture.SlideDown : ((this.anchor == Position.Top) ? CraftGUITexture.SlideUp : ((this.anchor == Position.LEFT) ? CraftGUITexture.SlideLeft : CraftGUITexture.SlideRight));
		CraftGUI.render.texture(slideTexture, new Point((this.anchor.x() + 1) * this.width() / 2 - 8, (this.anchor.y() + 1) * this.height() / 2 - 8));
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
	public boolean isMouseOverWidget(final Point relativeMouse) {
		return this.getArea().outset(this.isSlideActive() ? 16 : 8).outset(new Border(this.anchor.opposite(), 16)).contains(relativeMouse);
	}

	@Override
	public boolean isChildVisible(final IWidget child) {
		return this.slideActive;
	}

	public void setSlide(final boolean b) {
		this.slideActive = b;
		final Area area = this.isSlideActive() ? this.expanded : this.shrunk;
		this.setSize(area.size());
		this.setPosition(area.pos());
	}

	public void setLabel(final String l) {
		this.label = l;
	}
}
