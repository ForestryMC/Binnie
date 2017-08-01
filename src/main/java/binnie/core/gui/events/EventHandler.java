package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public abstract class EventHandler<E extends Event> {
	Class<? extends E> eventClass;
	Origin origin;
	IWidget relative;

	public EventHandler(Class<? extends E> eventClass) {
		this.origin = Origin.ANY;
		this.relative = null;
		this.eventClass = eventClass;
	}

	public EventHandler<E> setOrigin(Origin origin, IWidget relative) {
		this.origin = origin;
		this.relative = relative;
		return this;
	}

	public abstract void onEvent(E event);

	public final boolean handles(Event event) {
		return this.eventClass.isInstance(event) && this.origin.isOrigin(event.getOrigin(), this.relative);
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
