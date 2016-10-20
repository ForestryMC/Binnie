package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control {
    protected final IControlScrollable scrollable;

    public ControlScrollBar(final ControlScroll parent) {
        this(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), parent.getScrollableWidget());
    }

    public ControlScrollBar(final IWidget parent, final float x, final float y, final float w, final float h, final IControlScrollable scrollable2) {
        super(parent, x, y, w, h);
        this.scrollable = scrollable2;
        this.addAttribute(Attribute.MouseOver);
        this.addSelfEventHandler(new EventMouse.Drag.Handler() {
            @Override
            public void onEvent(final EventMouse.Drag event) {
                ControlScrollBar.this.scrollable.movePercentage(event.getDy() / (ControlScrollBar.this.h() - ControlScrollBar.this.getBarHeight()));
            }
        });
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                final float shownPercentage = ControlScrollBar.this.scrollable.getPercentageShown();
                final float percentageIndex = ControlScrollBar.this.scrollable.getPercentageIndex();
                final float minPercent = (1.0f - shownPercentage) * percentageIndex;
                final float maxPercent = minPercent + shownPercentage;
                float clickedPercentage = ControlScrollBar.this.getRelativeMousePosition().y() / (ControlScrollBar.this.h() - 2.0f);
                clickedPercentage = Math.max(Math.min(clickedPercentage, 1.0f), 0.0f);
                if (clickedPercentage > maxPercent) {
                    ControlScrollBar.this.scrollable.setPercentageIndex((clickedPercentage - shownPercentage) / (1.0f - shownPercentage));
                }
                if (clickedPercentage < minPercent) {
                    ControlScrollBar.this.scrollable.setPercentageIndex(clickedPercentage / (1.0f - shownPercentage));
                }
            }
        });
    }

    @Override
    public void onUpdateClient() {
    }

    @Override
    public boolean isEnabled() {
        return this.scrollable.getPercentageShown() < 0.99f;
    }

    public float getBarHeight() {
        return this.h() * this.scrollable.getPercentageShown();
    }

    protected IArea getRenderArea() {
        float height = this.getBarHeight();
        if (height < 6.0f) {
            height = 6.0f;
        }
        final float yOffset = ((int) this.h() - (int) this.getBarHeight()) * this.scrollable.getPercentageIndex();
        return new IArea(0.0f, yOffset, this.getSize().x(), height);
    }

    @Override
    public void onRenderBackground() {
        final IArea renderArea = this.getRenderArea();
        Object texture = CraftGUITexture.ScrollDisabled;
        if (this.isMouseOver()) {
            texture = CraftGUITexture.ScrollHighlighted;
        } else if (this.isEnabled()) {
            texture = CraftGUITexture.Scroll;
        }
        CraftGUI.Render.texture(texture, renderArea);
    }
}
