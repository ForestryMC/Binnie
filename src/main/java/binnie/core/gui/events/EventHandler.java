package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public abstract class EventHandler<E extends Event> {
	Class<E> eventClass;
	Origin origin;
	IWidget relative;

	public EventHandler(final Class<E> eventClass) {
		this.origin = Origin.ANY;
		this.relative = null;
		this.eventClass = eventClass;
	}

	public EventHandler<E> setOrigin(final Origin origin, final IWidget relative) {
		this.origin = origin;
		this.relative = relative;
		return this;
	}

	public abstract void onEvent(final E p0);

	public final boolean handles(final Event e) {
		return this.eventClass.isInstance(e) && this.origin.isOrigin(e.getOrigin(), this.relative);
	}

	public enum Origin {
		ANY,
		SELF,
		PARENT,
		DIRECT_CHILD;

		public boolean isOrigin(final IWidget origin, final IWidget test) {
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
