package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventCycleChanged extends Event {
    public int value;

    public EventCycleChanged(IWidget origin, int value) {
        super(origin);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
