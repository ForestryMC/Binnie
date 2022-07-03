package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public abstract class EventMouse extends Event {
    public EventMouse(IWidget origin) {
        super(origin);
    }

    public static class Button extends EventMouse {
        protected int x;
        protected int y;
        protected int button;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getButton() {
            return button;
        }

        public Button(IWidget currentMousedOverWidget, int x, int y, int button) {
            super(currentMousedOverWidget);
            this.x = x;
            this.y = y;
            this.button = button;
        }
    }

    public static class Down extends Button {
        public Down(IWidget currentMousedOverWidget, int x, int y, int button) {
            super(currentMousedOverWidget, x, y, button);
        }

        public abstract static class Handler extends EventHandler<Down> {
            public Handler() {
                super(Down.class);
            }
        }
    }

    public static class Up extends Button {
        public Up(IWidget currentMousedOverWidget, int x, int y, int button) {
            super(currentMousedOverWidget, x, y, button);
        }

        public abstract static class Handler extends EventHandler<Up> {
            public Handler() {
                super(Up.class);
            }
        }
    }

    public static class Move extends EventMouse {
        protected float dx;
        protected float dy;

        public float getDx() {
            return dx;
        }

        public float getDy() {
            return dy;
        }

        public Move(IWidget origin, float dx, float dy) {
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
        public Drag(IWidget draggedWidget, float dx, float dy) {
            super(draggedWidget, dx, dy);
        }

        public abstract static class Handler extends EventHandler<Drag> {
            public Handler() {
                super(Drag.class);
            }
        }
    }

    public static class Wheel extends EventMouse {
        protected int dWheel;

        public Wheel(IWidget origin, int dWheel) {
            super(origin);
            this.dWheel = 0;
            this.dWheel = dWheel / 28;
        }

        public int getDWheel() {
            return dWheel;
        }

        public abstract static class Handler extends EventHandler<Wheel> {
            public Handler() {
                super(Wheel.class);
            }
        }
    }
}
