package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventTextEdit extends EventValueChanged<String> {
    public EventTextEdit(IWidget origin, String text) {
        super(origin, text);
    }

    public abstract static class Handler extends EventHandler<EventTextEdit> {
        public Handler() {
            super(EventTextEdit.class);
        }
    }
}
