// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import org.lwjgl.opengl.GL11;

public class ControlSlide extends Control
{
	private IArea expanded;
	private IArea shrunk;
	private boolean slideActive;
	private Position anchor;
	private String label;

	public ControlSlide(final IWidget parent, final float x, final float y, final float w, final float h, final Position anchor2) {
		super(parent, x, y, w, h);
		slideActive = true;
		label = null;
		addAttribute(Attribute.MouseOver);
		addAttribute(Attribute.BlockTooltip);
		expanded = new IArea(getPosition(), getSize());
		anchor = anchor2.opposite();
		final float border = (anchor.x() != 0) ? (expanded.w() - 6.0f) : (expanded.h() - 6.0f);
		shrunk = expanded.inset(new IBorder(anchor, border));
		slideActive = false;
	}

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		if (label != null) {
			final float lw = CraftGUI.Render.textWidth(label) + 16;
			final float lh = CraftGUI.Render.textHeight() + 16;
			final boolean hor = anchor.x() != 0;
			final IArea ar = isSlideActive() ? expanded : shrunk;
			IArea tabArea = new IArea(hor ? (-lh / 2.0f) : (-lw / 2.0f), hor ? (-lw / 2.0f) : (-lh / 2.0f), hor ? lh : lw, hor ? lw : lh);
			final IPoint shift = new IPoint(ar.w() * (1 - anchor.x()) / 2.0f, ar.h() * (1 - anchor.y()) / 2.0f);
			tabArea = tabArea.shift(shift.x() - (-3.0f + lh / 2.0f) * anchor.x(), shift.y() - (-3.0f + lh / 2.0f) * anchor.y());
			Texture texture = CraftGUI.Render.getTexture(isSlideActive() ? CraftGUITexture.Tab : CraftGUITexture.TabDisabled).crop(anchor.opposite(), 8.0f);
			CraftGUI.Render.texture(texture, tabArea);
			texture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline).crop(anchor.opposite(), 8.0f);
			CraftGUI.Render.texture(texture, tabArea.inset(2));
			final IArea labelArea = new IArea(-lw / 2.0f, 0.0f, lw, lh);
			GL11.glPushMatrix();
			GL11.glTranslatef(shift.x() + anchor.x() * 2.0f, shift.y() + anchor.y() * 2.0f, 0.0f);
			if (anchor.x() != 0) {
				GL11.glRotatef(90.0f, 0.0f, 0.0f, anchor.x());
			}
			if (anchor.y() > 0) {
				GL11.glTranslatef(0.0f, -lh, 0.0f);
			}
			CraftGUI.Render.text(labelArea, TextJustification.MiddleCenter, label, 16777215);
			GL11.glPopMatrix();
		}
		CraftGUI.Render.texture(CraftGUITexture.Window, getArea());
		final Object slideTexture = (anchor == Position.Bottom) ? CraftGUITexture.SlideDown : ((anchor == Position.Top) ? CraftGUITexture.SlideUp : ((anchor == Position.Left) ? CraftGUITexture.SlideLeft : CraftGUITexture.SlideRight));
		CraftGUI.Render.texture(slideTexture, new IPoint((anchor.x() + 1.0f) * w() / 2.0f - 8.0f, (anchor.y() + 1.0f) * h() / 2.0f - 8.0f));
	}

	public boolean isSlideActive() {
		return slideActive;
	}

	@Override
	public void onUpdateClient() {
		final boolean mouseOver = isMouseOverWidget(getRelativeMousePosition());
		if (mouseOver != slideActive) {
			setSlide(mouseOver);
		}
	}

	@Override
	public boolean isMouseOverWidget(final IPoint relativeMouse) {
		return getArea().outset(isSlideActive() ? 16 : 8).outset(new IBorder(anchor.opposite(), 16.0f)).contains(relativeMouse);
	}

	@Override
	public boolean isChildVisible(final IWidget child) {
		return slideActive;
	}

	public void setSlide(final boolean b) {
		slideActive = b;
		final IArea area = isSlideActive() ? expanded : shrunk;
		setSize(area.size());
		setPosition(area.pos());
	}

	public void setLabel(final String l) {
		label = l;
	}
}
