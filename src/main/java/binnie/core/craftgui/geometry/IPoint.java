// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.geometry;

public class IPoint
{
	public static IPoint ZERO;
	float x;
	float y;

	public IPoint(float x, float y) {
		this.x = 0.0f;
		this.y = 0.0f;
		this.x = x;
		this.y = y;
	}

	public IPoint(IPoint o) {
		x = 0.0f;
		y = 0.0f;
		x = o.x();
		y = o.y();
	}

	public static IPoint add(IPoint a, IPoint b) {
		return new IPoint(a.x() + b.x(), a.y() + b.y());
	}

	public static IPoint sub(IPoint a, IPoint b) {
		return new IPoint(a.x() - b.x(), a.y() - b.y());
	}

	public IPoint sub(IPoint a) {
		return sub(this, a);
	}

	public IPoint add(IPoint other) {
		return add(this, other);
	}

	public IPoint add(float dx, float dy) {
		return add(this, new IPoint(dx, dy));
	}

	public IPoint copy() {
		return new IPoint(this);
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public void xy(float x, float y) {
		x(x);
		y(y);
	}

	public float x(float x) {
		return this.x = x;
	}

	public float y(float y) {
		return this.y = y;
	}

	public boolean equals(IPoint other) {
		return x() == other.x() && y() == other.y();
	}

	static {
		ZERO = new IPoint(0.0f, 0.0f);
	}
}
