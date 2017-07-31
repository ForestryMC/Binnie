package binnie.core.gui.events;

import binnie.core.gui.IWidget;

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
