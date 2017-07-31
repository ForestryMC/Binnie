package binnie.core.gui.events;

import binnie.core.gui.IWidget;

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
