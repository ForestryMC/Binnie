package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

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
