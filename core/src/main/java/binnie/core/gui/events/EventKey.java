package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public abstract class EventKey extends Event {
	char character;
	int key;

	public EventKey(final IWidget origin, final char character, final int key) {
		super(origin);
		this.character = character;
		this.key = key;
	}

	public char getCharacter() {
		return this.character;
	}

	public int getKey() {
		return this.key;
	}

	public static class Down extends EventKey {
		public Down(final IWidget origin, final char character, final int key) {
			super(origin, character, key);
		}
	}
}
