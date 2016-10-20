package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

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

        public abstract static class Handler extends EventHandler<Down> {
            public Handler() {
                super(Down.class);
            }
        }
    }

    public static class Up extends EventKey {
        public Up(final IWidget origin, final char character, final int key) {
            super(origin, character, key);
        }

        public abstract static class Handler extends EventHandler<Up> {
            public Handler() {
                super(Up.class);
            }
        }
    }
}
