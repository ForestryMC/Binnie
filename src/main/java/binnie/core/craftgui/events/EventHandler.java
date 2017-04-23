// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public abstract class EventHandler<E extends Event>
{
	Class<E> eventClass;
	Origin origin;
	IWidget relative;

	public EventHandler(final Class<E> eventClass) {
		origin = Origin.Any;
		relative = null;
		this.eventClass = eventClass;
	}

	public EventHandler setOrigin(final Origin origin, final IWidget relative) {
		this.origin = origin;
		this.relative = relative;
		return this;
	}

	public abstract void onEvent(final E p0);

	public final boolean handles(final Event e) {
		return eventClass.isInstance(e) && origin.isOrigin(e.getOrigin(), relative);
	}

	public enum Origin
	{
		Any,
		Self,
		Parent,
		DirectChild;

		public boolean isOrigin(final IWidget origin, final IWidget test) {
			switch (this) {
			case Any: {
				return true;
			}
			case DirectChild: {
				return test.getWidgets().contains(origin);
			}
			case Parent: {
				return test.getParent() == origin;
			}
			case Self: {
				return test == origin;
			}
			default: {
				return false;
			}
			}
		}
	}
}
