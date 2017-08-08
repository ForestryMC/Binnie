package binnie.core.gui.events;

import javax.annotation.Nullable;

import binnie.core.gui.IWidget;

public final class EventHandler<E extends Event> {
	private final OnEventHandler<E> onEventHandler;
	private final Class<? super E> eventClass;
	private final Origin origin;
	@Nullable
	private final IWidget relative;

	public EventHandler(Class<? super E> eventClass, OnEventHandler<E> onEventHandler) {
		this.origin = Origin.ANY;
		this.relative = null;
		this.eventClass = eventClass;
		this.onEventHandler = onEventHandler;
	}

	public EventHandler(Class<? super E> eventClass, Origin origin, IWidget relative, OnEventHandler<E> onEventHandler) {
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

	public interface OnEventHandler<E> {
		void onEvent(E event);
	}

	public enum Origin {
		ANY,
		SELF,
		PARENT,
		DIRECT_CHILD;

		public boolean isOrigin(IWidget origin, IWidget test) {
			switch (this) {
				case ANY: {
					return true;
				}
				case DIRECT_CHILD: {
					return test.getChildren().contains(origin);
				}
				case PARENT: {
					return test.getParent() == origin;
				}
				case SELF: {
					return test == origin;
				}
				default: {
					return false;
				}
			}
		}
	}
}
