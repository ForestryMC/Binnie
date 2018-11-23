package binnie.core.api.gui.events;

import binnie.core.api.gui.IWidget;

public class Event {
	private final IWidget origin;

	public Event(IWidget origin) {
		this.origin = origin;
	}

	public IWidget getOrigin() {
		return this.origin;
	}

	public boolean isOrigin(IWidget widget) {
		return this.origin == widget;
	}
}
