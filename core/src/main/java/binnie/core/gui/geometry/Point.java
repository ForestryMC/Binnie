package binnie.core.gui.geometry;

import binnie.core.api.gui.IPoint;

public class Point implements IPoint {
	public static final Point ZERO = new Point(0, 0);

	private final int xPos;
	private final int yPos;

	public Point(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Point(IPoint point) {
		this.xPos = point.xPos();
		this.yPos = point.yPos();
	}

	public static IPoint add(IPoint firstPoint, IPoint secondPoint) {
		return new Point(firstPoint.xPos() + secondPoint.xPos(), firstPoint.yPos() + secondPoint.yPos());
	}

	public static IPoint sub(IPoint firstPoint, IPoint secondPoint) {
		return new Point(firstPoint.xPos() - secondPoint.xPos(), firstPoint.yPos() - secondPoint.yPos());
	}

	@Override
	public IPoint sub(IPoint point) {
		return sub(this, point);
	}

	@Override
	public IPoint sub(int xPos, int yPos) {
		return new Point(xPos() - xPos, yPos() - yPos);
	}

	@Override
	public IPoint add(IPoint other) {
		return add(this, other);
	}

	@Override
	public IPoint add(int xPos, int yPos) {
		return new Point(xPos() + xPos, yPos() + yPos);
	}

	@Override
	public IPoint copy() {
		return new Point(this);
	}

	@Override
	public int xPos() {
		return this.xPos;
	}

	@Override
	public int yPos() {
		return this.yPos;
	}

	@Override
	public boolean equals(final IPoint other) {
		return this.xPos() == other.xPos() && this.yPos() == other.yPos();
	}
}
