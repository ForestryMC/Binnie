package binnie.craftgui.core.geometry;

public enum Position {
	Top(0, -1),
	BOTTOM(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	int x;
	int y;

	Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public Position opposite() {
		switch (this) {
			case BOTTOM: {
				return Position.Top;
			}
			case LEFT: {
				return Position.RIGHT;
			}
			case RIGHT: {
				return Position.LEFT;
			}
			case Top: {
				return Position.BOTTOM;
			}
			default: {
				throw new IllegalStateException();
			}
		}
	}
}
