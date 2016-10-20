package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public abstract class EventHandler<E extends Event> {
    Class<E> eventClass;
    Origin origin;
    IWidget relative;

    public EventHandler(final Class<E> eventClass) {
        this.origin = Origin.Any;
        this.relative = null;
        this.eventClass = eventClass;
    }

    public EventHandler setOrigin(final Origin origin, final IWidget relative) {
        this.origin = origin;
        this.relative = relative;
        return this;
    }

    public abstract void onEvent(final E p0);

    public final boolean handles(final Event e) {
        return this.eventClass.isInstance(e) && this.origin.isOrigin(e.getOrigin(), this.relative);
    }

    public enum Origin {
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
