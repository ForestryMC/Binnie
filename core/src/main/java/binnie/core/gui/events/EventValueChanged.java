package binnie.core.gui.events;

import javax.annotation.Nullable;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public class EventValueChanged<T> extends Event {
	@Nullable
	private final T value;

	public EventValueChanged(final IWidget origin, @Nullable final T value) {
		super(origin);
		this.value = value;
	}

	@Nullable
	public T getValue() {
		return this.value;
	}
}
