package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public class EventCycleChanged extends Event {
    public int value;

    public EventCycleChanged(final IWidget origin, final int value) {
        super(origin);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
