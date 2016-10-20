package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import org.lwjgl.opengl.GL11;

public class ControlSlide extends Control {
    private IArea expanded;
    private IArea shrunk;
    private boolean slideActive;
    private Position anchor;
    private String label;

    public ControlSlide(final IWidget parent, final float x, final float y, final float w, final float h, final Position anchor2) {
        super(parent, x, y, w, h);
        this.slideActive = true;
        this.label = null;
        this.addAttribute(Attribute.MouseOver);
        this.addAttribute(Attribute.BlockTooltip);
        this.expanded = new IArea(this.getPosition(), this.getSize());
        this.anchor = anchor2.opposite();
        final float border = (this.anchor.x() != 0) ? (this.expanded.w() - 6.0f) : (this.expanded.h() - 6.0f);
        this.shrunk = this.expanded.inset(new IBorder(this.anchor, border));
        this.slideActive = false;
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        if (this.label != null) {
            final float lw = CraftGUI.Render.textWidth(this.label) + 16;
            final float lh = CraftGUI.Render.textHeight() + 16;
            final boolean hor = this.anchor.x() != 0;
            final IArea ar = this.isSlideActive() ? this.expanded : this.shrunk;
            IArea tabArea = new IArea(hor ? (-lh / 2.0f) : (-lw / 2.0f), hor ? (-lw / 2.0f) : (-lh / 2.0f), hor ? lh : lw, hor ? lw : lh);
            final IPoint shift = new IPoint(ar.w() * (1 - this.anchor.x()) / 2.0f, ar.h() * (1 - this.anchor.y()) / 2.0f);
            tabArea = tabArea.shift(shift.x() - (-3.0f + lh / 2.0f) * this.anchor.x(), shift.y() - (-3.0f + lh / 2.0f) * this.anchor.y());
            Texture texture = CraftGUI.Render.getTexture(this.isSlideActive() ? CraftGUITexture.Tab : CraftGUITexture.TabDisabled).crop(this.anchor.opposite(), 8.0f);
            CraftGUI.Render.texture(texture, tabArea);
            texture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline).crop(this.anchor.opposite(), 8.0f);
            CraftGUI.Render.texture(texture, tabArea.inset(2));
            final IArea labelArea = new IArea(-lw / 2.0f, 0.0f, lw, lh);
            GL11.glPushMatrix();
            GL11.glTranslatef(shift.x() + this.anchor.x() * 2.0f, shift.y() + this.anchor.y() * 2.0f, 0.0f);
            if (this.anchor.x() != 0) {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, this.anchor.x());
            }
            if (this.anchor.y() > 0) {
                GL11.glTranslatef(0.0f, -lh, 0.0f);
            }
            CraftGUI.Render.text(labelArea, TextJustification.MiddleCenter, this.label, 16777215);
            GL11.glPopMatrix();
        }
        CraftGUI.Render.texture(CraftGUITexture.Window, this.getArea());
        final Object slideTexture = (this.anchor == Position.Bottom) ? CraftGUITexture.SlideDown : ((this.anchor == Position.Top) ? CraftGUITexture.SlideUp : ((this.anchor == Position.Left) ? CraftGUITexture.SlideLeft : CraftGUITexture.SlideRight));
        CraftGUI.Render.texture(slideTexture, new IPoint((this.anchor.x() + 1.0f) * this.w() / 2.0f - 8.0f, (this.anchor.y() + 1.0f) * this.h() / 2.0f - 8.0f));
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
        return this.getArea().outset(this.isSlideActive() ? 16 : 8).outset(new IBorder(this.anchor.opposite(), 16.0f)).contains(relativeMouse);
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
