package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public class EventButtonClicked extends Event {
	public EventButtonClicked(IWidget origin) {
		super(origin);
	}
}
