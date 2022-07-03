package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class Event {
    IWidget origin;

    public Event(IWidget origin) {
        this.origin = origin;
    }

    public IWidget getOrigin() {
        return origin;
    }

    public boolean isOrigin(IWidget widget) {
        return origin == widget;
    }
}
