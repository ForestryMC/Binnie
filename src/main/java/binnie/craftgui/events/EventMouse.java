package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public abstract class EventMouse extends Event {
    public EventMouse(final IWidget origin) {
        super(origin);
    }

    public static class Button extends EventMouse {
        int x;
        int y;
        int button;

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getButton() {
            return this.button;
        }

        public Button(final IWidget currentMousedOverWidget, final int x, final int y, final int button) {
            super(currentMousedOverWidget);
            this.x = x;
            this.y = y;
            this.button = button;
        }
    }

    public static class Down extends Button {
        public Down(final IWidget currentMousedOverWidget, final int x, final int y, final int button) {
            super(currentMousedOverWidget, x, y, button);
        }

        public abstract static class Handler extends EventHandler<Down> {
            public Handler() {
                super(Down.class);
            }
        }
    }

    public static class Up extends Button {
        public Up(final IWidget currentMousedOverWidget, final int x, final int y, final int button) {
            super(currentMousedOverWidget, x, y, button);
        }

        public abstract static class Handler extends EventHandler<Up> {
            public Handler() {
                super(Up.class);
            }
        }
    }

    public static class Move extends EventMouse {
        float dx;
        float dy;

        public float getDx() {
            return this.dx;
        }

        public float getDy() {
            return this.dy;
        }

        public Move(final IWidget origin, final float dx, final float dy) {
            super(origin);
            this.dx = dx;
            this.dy = dy;
        }

        public abstract static class Handler extends EventHandler<Move> {
            public Handler() {
                super(Move.class);
            }
        }
    }

    public static class Drag extends Move {
        public Drag(final IWidget draggedWidget, final float dx, final float dy) {
            super(draggedWidget, dx, dy);
        }

        public abstract static class Handler extends EventHandler<Drag> {
            public Handler() {
                super(Drag.class);
            }
        }
    }

    public static class Wheel extends EventMouse {
        int dWheel;

        public Wheel(final IWidget origin, final int dWheel) {
            super(origin);
            this.dWheel = 0;
            this.dWheel = dWheel / 28;
        }

        public int getDWheel() {
            return this.dWheel;
        }

        public abstract static class Handler extends EventHandler<Wheel> {
            public Handler() {
                super(Wheel.class);
            }
        }
    }
}
