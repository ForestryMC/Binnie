package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public abstract class EventKey extends Event {
    char character;
    int key;

    public EventKey(IWidget origin, char character, int key) {
        super(origin);
        this.character = character;
        this.key = key;
    }

    public char getCharacter() {
        return character;
    }

    public int getKey() {
        return key;
    }

    public static class Down extends EventKey {
        public Down(IWidget origin, char character, int key) {
            super(origin, character, key);
        }

        public abstract static class Handler extends EventHandler<Down> {
            public Handler() {
                super(Down.class);
            }
        }
    }
}
