package binnie.craftgui.core.geometry;

public class Point {
	public static final Point ZERO = new Point(0, 0);
	private final int x;
	private final int y;

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Point(final Point o) {
		this.x = o.x();
		this.y = o.y();
	}

	public static Point add(final Point a, final Point b) {
		return new Point(a.x() + b.x(), a.y() + b.y());
	}

	public static Point sub(final Point a, final Point b) {
		return new Point(a.x() - b.x(), a.y() - b.y());
	}

	public Point sub(final Point a) {
		return sub(this, a);
	}

	public Point add(final Point other) {
		return add(this, other);
	}

	public Point add(final int dx, final int dy) {
		return add(this, new Point(dx, dy));
	}

	public Point copy() {
		return new Point(this);
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public boolean equals(final Point other) {
		return this.x() == other.x() && this.y() == other.y();
	}

}
