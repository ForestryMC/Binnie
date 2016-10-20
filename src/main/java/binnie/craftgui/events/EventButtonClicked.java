package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public class EventButtonClicked extends Event {
    public EventButtonClicked(final IWidget origin) {
        super(origin);
    }

    public abstract static class Handler extends EventHandler<EventButtonClicked> {
        public Handler() {
            super(EventButtonClicked.class);
        }
    }
}
