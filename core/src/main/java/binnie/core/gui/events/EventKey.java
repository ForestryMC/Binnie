package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public abstract class EventKey extends Event {
	private final char character;
	private final int key;

	public EventKey(IWidget origin, char character, int key) {
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
		public Down(IWidget origin, char character, int key) {
			super(origin, character, key);
		}
	}
}
