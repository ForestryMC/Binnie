package binnie.core.gui.events;

import binnie.core.gui.IWidget;

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

	public static class Up extends EventKey {
		public Up(final IWidget origin, final char character, final int key) {
			super(origin, character, key);
		}
	}
}
