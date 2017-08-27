package binnie.core.api.gui;

public enum Alignment {
	TOP(0, -1),
	BOTTOM(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	private final int x;
	private final int y;

	Alignment(int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public Alignment opposite() {
		switch (this) {
			case BOTTOM: {
				return Alignment.TOP;
			}
			case LEFT: {
				return Alignment.RIGHT;
			}
			case RIGHT: {
				return Alignment.LEFT;
			}
			case TOP: {
				return Alignment.BOTTOM;
			}
			default: {
				throw new IllegalStateException();
			}
		}
	}
}
