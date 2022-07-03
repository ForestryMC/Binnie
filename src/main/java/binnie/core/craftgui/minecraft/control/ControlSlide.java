package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import org.lwjgl.opengl.GL11;

public class ControlSlide extends Control {
    private IArea expanded;
    private IArea shrunk;
    private boolean slideActive;
    private Position anchor;
    private String label;

    public ControlSlide(IWidget parent, float x, float y, float w, float h, Position anchor2) {
        super(parent, x, y, w, h);
        slideActive = true;
        label = null;
        addAttribute(WidgetAttribute.MOUSE_OVER);
        addAttribute(WidgetAttribute.BLOCK_TOOLTIP);
        expanded = new IArea(getPosition(), getSize());
        anchor = anchor2.opposite();
        float border = (anchor.x() != 0) ? (expanded.w() - 6.0f) : (expanded.h() - 6.0f);
        shrunk = expanded.inset(new IBorder(anchor, border));
        slideActive = false;
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        if (label != null) {
            float lw = CraftGUI.render.textWidth(label) + 16;
            float lh = CraftGUI.render.textHeight() + 16;
            boolean hor = anchor.x() != 0;
            IArea ar = isSlideActive() ? expanded : shrunk;
            IArea tabArea = new IArea(
                    hor ? (-lh / 2.0f) : (-lw / 2.0f), hor ? (-lw / 2.0f) : (-lh / 2.0f), hor ? lh : lw, hor ? lw : lh);
            IPoint shift = new IPoint(ar.w() * (1 - anchor.x()) / 2.0f, ar.h() * (1 - anchor.y()) / 2.0f);
            tabArea = tabArea.shift(
                    shift.x() - (-3.0f + lh / 2.0f) * anchor.x(), shift.y() - (-3.0f + lh / 2.0f) * anchor.y());
            Texture texture = CraftGUI.render
                    .getTexture(isSlideActive() ? CraftGUITexture.Tab : CraftGUITexture.TabDisabled)
                    .crop(anchor.opposite(), 8.0f);
            CraftGUI.render.texture(texture, tabArea);
            texture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline).crop(anchor.opposite(), 8.0f);
            CraftGUI.render.texture(texture, tabArea.inset(2));
            IArea labelArea = new IArea(-lw / 2.0f, 0.0f, lw, lh);
            GL11.glPushMatrix();
            GL11.glTranslatef(shift.x() + anchor.x() * 2.0f, shift.y() + anchor.y() * 2.0f, 0.0f);
            if (anchor.x() != 0) {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, anchor.x());
            }
            if (anchor.y() > 0) {
                GL11.glTranslatef(0.0f, -lh, 0.0f);
            }
            CraftGUI.render.text(labelArea, TextJustification.MIDDLE_CENTER, label, 16777215);
            GL11.glPopMatrix();
        }
        CraftGUI.render.texture(CraftGUITexture.Window, getArea());
        Object slideTexture = (anchor == Position.BOTTOM)
                ? CraftGUITexture.SlideDown
                : ((anchor == Position.TOP)
                        ? CraftGUITexture.SlideUp
                        : ((anchor == Position.LEFT) ? CraftGUITexture.SlideLeft : CraftGUITexture.SlideRight));
        CraftGUI.render.texture(
                slideTexture,
                new IPoint((anchor.x() + 1.0f) * w() / 2.0f - 8.0f, (anchor.y() + 1.0f) * h() / 2.0f - 8.0f));
    }

    public boolean isSlideActive() {
        return slideActive;
    }

    @Override
    public void onUpdateClient() {
        boolean mouseOver = isMouseOverWidget(getRelativeMousePosition());
        if (mouseOver != slideActive) {
            setSlide(mouseOver);
        }
    }

    @Override
    public boolean isMouseOverWidget(IPoint relativeMouse) {
        return getArea()
                .outset(isSlideActive() ? 16 : 8)
                .outset(new IBorder(anchor.opposite(), 16.0f))
                .contains(relativeMouse);
    }

    @Override
    public boolean isChildVisible(IWidget child) {
        return slideActive;
    }

    public void setSlide(boolean b) {
        slideActive = b;
        IArea area = isSlideActive() ? expanded : shrunk;
        setSize(area.size());
        setPosition(area.pos());
    }

    public void setLabel(String l) {
        label = l;
    }
}
