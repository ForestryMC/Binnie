package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public abstract class EventMouse extends Event {
	public EventMouse(IWidget origin) {
		super(origin);
	}

	public static class Button extends EventMouse {
		private final int x;
		private final int y;
		private final int button;

		public Button(IWidget currentMousedOverWidget, int x, int y, int button) {
			super(currentMousedOverWidget);
			this.x = x;
			this.y = y;
			this.button = button;
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public int getButton() {
			return this.button;
		}
	}

	public static class Down extends Button {
		public Down(IWidget currentMousedOverWidget, int x, int y, int button) {
			super(currentMousedOverWidget, x, y, button);
		}
	}

	public static class Up extends Button {
		public Up(IWidget currentMousedOverWidget, int x, int y, int button) {
			super(currentMousedOverWidget, x, y, button);
		}
	}

	public static class Move extends EventMouse {
		private final float dx;
		private final float dy;

		public Move(IWidget origin, float dx, float dy) {
			super(origin);
			this.dx = dx;
			this.dy = dy;
		}

		public float getDx() {
			return this.dx;
		}

		public float getDy() {
			return this.dy;
		}
	}

	public static class Drag extends Move {
		public Drag(IWidget draggedWidget, float dx, float dy) {
			super(draggedWidget, dx, dy);
		}
	}

	public static class Wheel extends EventMouse {
		private int dWheel;

		public Wheel(IWidget origin, int dWheel) {
			super(origin);
			this.dWheel = 0;
			this.dWheel = dWheel / 28;
		}

		public int getDWheel() {
			return this.dWheel;
		}
	}
}
