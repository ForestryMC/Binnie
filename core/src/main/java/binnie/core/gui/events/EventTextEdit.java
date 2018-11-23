package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;

public class EventTextEdit extends EventValueChanged<String> {
	public EventTextEdit(IWidget origin, String text) {
		super(origin, text);
	}
}
