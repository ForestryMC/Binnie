// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.geometry;

public enum Position
{
	Top(0, -1),
	Bottom(0, 1),
	Left(-1, 0),
	Right(1, 0);

	int x;
	int y;

	Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public Position opposite() {
		switch (this) {
		case Bottom: {
			return Position.Top;
		}
		case Left: {
			return Position.Right;
		}
		case Right: {
			return Position.Left;
		}
		case Top: {
			return Position.Bottom;
		}
		default: {
			return null;
		}
		}
	}
}
