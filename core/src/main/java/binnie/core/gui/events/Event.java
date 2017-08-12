package binnie.core.gui.events;

import binnie.core.gui.IWidget;

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
