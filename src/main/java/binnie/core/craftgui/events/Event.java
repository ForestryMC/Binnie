package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

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
