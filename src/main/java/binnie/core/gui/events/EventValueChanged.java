package binnie.core.gui.events;

import javax.annotation.Nullable;

import binnie.core.gui.IWidget;

public class EventValueChanged<T> extends Event {
	@Nullable
	private T value;

	public EventValueChanged(final IWidget origin, @Nullable final T value) {
		super(origin);
		this.value = value;
	}

	@Nullable
	public T getValue() {
		return this.value;
	}
}
