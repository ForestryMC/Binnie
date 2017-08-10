package binnie.core.gui.geometry;

public class Point {
	public static final Point ZERO = new Point(0, 0);

	private final int xPos;
	private final int yPos;

	public Point(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Point(Point point) {
		this.xPos = point.xPos();
		this.yPos = point.yPos();
	}

	public static Point add(Point firstPoint, Point secondPoint) {
		return new Point(firstPoint.xPos() + secondPoint.xPos(), firstPoint.yPos() + secondPoint.yPos());
	}

	public static Point sub(Point firstPoint, Point secondPoint) {
		return new Point(firstPoint.xPos() - secondPoint.xPos(), firstPoint.yPos() - secondPoint.yPos());
	}

	public Point sub(Point point) {
		return sub(this, point);
	}

	public Point add(Point other) {
		return add(this, other);
	}

	public Point add(int xPos, int yPos) {
		return add(this, new Point(xPos, yPos));
	}

	public Point copy() {
		return new Point(this);
	}

	public int xPos() {
		return this.xPos;
	}

	public int yPos() {
		return this.yPos;
	}

	public boolean equals(final Point other) {
		return this.xPos() == other.xPos() && this.yPos() == other.yPos();
	}
}
