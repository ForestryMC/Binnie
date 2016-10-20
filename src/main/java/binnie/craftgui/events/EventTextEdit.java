package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public class EventTextEdit extends EventValueChanged<String> {
    public EventTextEdit(final IWidget origin, final String text) {
        super(origin, text);
    }

    public abstract static class Handler extends EventHandler<EventTextEdit> {
        public Handler() {
            super(EventTextEdit.class);
        }
    }
}
