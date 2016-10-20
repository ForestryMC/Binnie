package binnie.craftgui.core.geometry;

public class IPoint {
    public static final IPoint ZERO;
    float x;
    float y;

    public IPoint(final float x, final float y) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.x = x;
        this.y = y;
    }

    public IPoint(final IPoint o) {
        this.x = 0.0f;
        this.y = 0.0f;
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

    public IPoint add(final float dx, final float dy) {
        return add(this, new IPoint(dx, dy));
    }

    public IPoint copy() {
        return new IPoint(this);
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public void xy(final float x, final float y) {
        this.x(x);
        this.y(y);
    }

    public float x(final float x) {
        return this.x = x;
    }

    public float y(final float y) {
        return this.y = y;
    }

    public boolean equals(final IPoint other) {
        return this.x() == other.x() && this.y() == other.y();
    }

    static {
        ZERO = new IPoint(0.0f, 0.0f);
    }
}
