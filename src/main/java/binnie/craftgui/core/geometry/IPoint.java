package binnie.craftgui.core.geometry;

public class IPoint {
	public static final IPoint ZERO = new IPoint(0, 0);
	private final int x;
	private final int y;

	public IPoint(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public IPoint(final IPoint o) {
		this.x = o.x();
		this.y = o.y();
	}

	public static IPoint add(final IPoint a, final IPoint b) {
		return new IPoint(a.x() + b.x(), a.y() + b.y());
	}

	public static IPoint sub(final IPoint a, final IPoint b) {
		return new IPoint(a.x() - b.x(), a.y() - b.y());
	}

	public IPoint sub(final IPoint a) {
		return sub(this, a);
	}

	public IPoint add(final IPoint other) {
		return add(this, other);
	}

	public IPoint add(final int dx, final int dy) {
		return add(this, new IPoint(dx, dy));
	}

	public IPoint copy() {
		return new IPoint(this);
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public boolean equals(final IPoint other) {
		return this.x() == other.x() && this.y() == other.y();
	}

}
