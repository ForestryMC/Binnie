// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventValueChanged<T> extends Event
{
	public T value;

	public EventValueChanged(final IWidget origin, final T value) {
		super(origin);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public abstract static class Handler extends EventHandler<EventValueChanged>
	{
		public Handler() {
			super(EventValueChanged.class);
		}
	}
}
