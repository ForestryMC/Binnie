package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;

public class ControlScroll extends Control {
    private IControlScrollable scrollWidget;

    public ControlScroll(final IWidget parent, final float x, final float y, final float width, final float height, final IControlScrollable scrollWidget) {
        super(parent, x, y, width, height);
        this.scrollWidget = scrollWidget;
        new ControlScrollBar(this);
    }

    public IControlScrollable getScrollableWidget() {
        return this.scrollWidget;
    }
}
