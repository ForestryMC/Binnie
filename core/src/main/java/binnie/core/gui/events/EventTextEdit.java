package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;

public class EventTextEdit extends EventValueChanged<String> {
	public EventTextEdit(final IWidget origin, final String text) {
		super(origin, text);
	}
}
