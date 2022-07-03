package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control {
    protected IControlScrollable scrollable;

    public ControlScrollBar(ControlScroll parent) {
        this(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), parent.getScrollableWidget());
    }

    public ControlScrollBar(IWidget parent, float x, float y, float w, float h, IControlScrollable scrollable2) {
        super(parent, x, y, w, h);
        scrollable = scrollable2;
        addAttribute(WidgetAttribute.MOUSE_OVER);
        addSelfEventHandler(new EventMouse.Drag.Handler() {
            @Override
            public void onEvent(EventMouse.Drag event) {
                scrollable.movePercentage(event.getDy() / (h() - getBarHeight()));
            }
        });
        addSelfEventHandler(mouseDownHandler);
    }

    private EventMouse.Down.Handler mouseDownHandler = new EventMouse.Down.Handler() {
        @Override
        public void onEvent(EventMouse.Down event) {
            float shownPercentage = scrollable.getPercentageShown();
            float percentageIndex = scrollable.getPercentageIndex();
            float minPercent = (1.0f - shownPercentage) * percentageIndex;
            float maxPercent = minPercent + shownPercentage;
            float clickedPercentage = getRelativeMousePosition().y() / (h() - 2.0f);
            clickedPercentage = Math.max(Math.min(clickedPercentage, 1.0f), 0.0f);

            if (clickedPercentage > maxPercent) {
                scrollable.setPercentageIndex((clickedPercentage - shownPercentage) / (1.0f - shownPercentage));
            }
            if (clickedPercentage < minPercent) {
                scrollable.setPercentageIndex(clickedPercentage / (1.0f - shownPercentage));
            }
        }
    };

    @Override
    public void onUpdateClient() {
        // ignored
    }

    @Override
    public void onRenderBackground() {
        IArea renderArea = getRenderArea();
        Object texture = CraftGUITexture.ScrollDisabled;
        if (isMouseOver()) {
            texture = CraftGUITexture.ScrollHighlighted;
        } else if (isEnabled()) {
            texture = CraftGUITexture.Scroll;
        }
        CraftGUI.render.texture(texture, renderArea);
    }

    @Override
    public boolean isEnabled() {
        return scrollable.getPercentageShown() < 0.99f;
    }

    public float getBarHeight() {
        return h() * scrollable.getPercentageShown();
    }

    protected IArea getRenderArea() {
        float height = getBarHeight();
        if (height < 6.0f) {
            height = 6.0f;
        }

        float yOffset = ((int) h() - (int) getBarHeight()) * scrollable.getPercentageIndex();
        return new IArea(0.0f, yOffset, getSize().x(), height);
    }
}
