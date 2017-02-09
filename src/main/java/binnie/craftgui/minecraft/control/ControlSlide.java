package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlSlide extends Control {
	private IArea expanded;
	private IArea shrunk;
	private boolean slideActive;
	private Position anchor;
	private String label;

	public ControlSlide(final IWidget parent, final int x, final int y, final int w, final int h, final Position anchor2) {
		super(parent, x, y, w, h);
		this.slideActive = true;
		this.label = null;
		this.addAttribute(Attribute.MouseOver);
		this.addAttribute(Attribute.BlockTooltip);
		this.expanded = new IArea(this.getPosition(), this.getSize());
		this.anchor = anchor2.opposite();
		final int border = (this.anchor.x() != 0) ? (this.expanded.w() - 6) : (this.expanded.h() - 6);
		this.shrunk = this.expanded.inset(new IBorder(this.anchor, border));
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
			IArea tabArea = new IArea(hor ? (-lh / 2) : (-lw / 2), hor ? (-lw / 2) : (-lh / 2), hor ? lh : lw, hor ? lw : lh);
			final IPoint shift = new IPoint(ar.w() * (1 - this.anchor.x()) / 2, ar.h() * (1 - this.anchor.y()) / 2);
			tabArea = tabArea.shift(shift.x() - (-3 + lh / 2) * this.anchor.x(), shift.y() - (-3 + lh / 2) * this.anchor.y());
			Texture texture = CraftGUI.render.getTexture(this.isSlideActive() ? CraftGUITexture.Tab : CraftGUITexture.TabDisabled).crop(this.anchor.opposite(), 8);
			CraftGUI.render.texture(texture, tabArea);
			texture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline).crop(this.anchor.opposite(), 8);
			CraftGUI.render.texture(texture, tabArea.inset(2));
			final IArea labelArea = new IArea(-lw / 2, 0, lw, lh);
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
		final Object slideTexture = (this.anchor == Position.Bottom) ? CraftGUITexture.SlideDown : ((this.anchor == Position.Top) ? CraftGUITexture.SlideUp : ((this.anchor == Position.Left) ? CraftGUITexture.SlideLeft : CraftGUITexture.SlideRight));
		CraftGUI.render.texture(slideTexture, new IPoint((this.anchor.x() + 1) * this.w() / 2 - 8, (this.anchor.y() + 1) * this.h() / 2 - 8));
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
		return this.getArea().outset(this.isSlideActive() ? 16 : 8).outset(new IBorder(this.anchor.opposite(), 16)).contains(relativeMouse);
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
