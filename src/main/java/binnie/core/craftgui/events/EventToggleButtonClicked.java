package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventToggleButtonClicked extends Event {
    protected boolean toggled;

    public EventToggleButtonClicked(IWidget origin, boolean toggled) {
        super(origin);
        this.toggled = toggled;
    }

    public boolean isActive() {
        return toggled;
    }
}
