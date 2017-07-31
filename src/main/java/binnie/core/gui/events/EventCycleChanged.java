package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public class EventCycleChanged extends Event {
	public int value;

	public EventCycleChanged(final IWidget origin, final int value) {
		super(origin);
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
