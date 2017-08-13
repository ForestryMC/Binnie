package binnie.core.gui.events;

import javax.annotation.Nullable;

import binnie.core.api.gui.events.Event;
import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.core.api.gui.events.OnEventHandler;
import binnie.core.api.gui.IWidget;

public final class EventHandler<E extends Event> {
	private final OnEventHandler<E> onEventHandler;
	private final Class<? super E> eventClass;
	private final EventHandlerOrigin origin;
	@Nullable
	private final IWidget relative;

	public EventHandler(Class<? super E> eventClass, OnEventHandler<E> onEventHandler) {
		this.origin = EventHandlerOrigin.ANY;
		this.relative = null;
		this.eventClass = eventClass;
		this.onEventHandler = onEventHandler;
	}

	public EventHandler(Class<? super E> eventClass, EventHandlerOrigin origin, IWidget relative, OnEventHandler<E> onEventHandler) {
		this.origin = origin;
		this.relative = relative;
		this.eventClass = eventClass;
		this.onEventHandler = onEventHandler;
	}

	public final void onEvent(E event) {
		onEventHandler.onEvent(event);
	}

	public final boolean handles(Event event) {
		boolean instance = this.eventClass.isInstance(event);
		return instance && this.origin.isOrigin(event.getOrigin(), this.relative);
	}

}
