package binnie.core.api.gui.events;

import binnie.core.api.gui.IWidget;

public class Event {
	IWidget origin;

	public Event(final IWidget origin) {
		this.origin = origin;
	}

	public IWidget getOrigin() {
		return this.origin;
	}

	public boolean isOrigin(final IWidget widget) {
		return this.origin == widget;
	}
}
