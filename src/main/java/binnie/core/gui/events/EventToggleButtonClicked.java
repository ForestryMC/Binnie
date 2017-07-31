package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public class EventToggleButtonClicked extends Event {
	boolean toggled;

	public EventToggleButtonClicked(final IWidget origin, final boolean toggled) {
		super(origin);
		this.toggled = toggled;
	}

	public boolean isActive() {
		return this.toggled;
	}
}
