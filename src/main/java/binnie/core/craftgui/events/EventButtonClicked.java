package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventButtonClicked extends Event {
    public EventButtonClicked(IWidget origin) {
        super(origin);
    }

    public abstract static class Handler extends EventHandler<EventButtonClicked> {
        public Handler() {
            super(EventButtonClicked.class);
        }
    }
}
