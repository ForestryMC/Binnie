package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public class EventValuesChanged<T> extends Event {
	public T[] values;

	public EventValuesChanged(final IWidget origin, final T[] values) {
		super(origin);
		this.values = values;
	}

	public T[] getValues() {
		return this.values;
	}
}
