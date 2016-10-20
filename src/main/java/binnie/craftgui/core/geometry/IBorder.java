package binnie.craftgui.core.geometry;

public class IBorder {
    public static final IBorder ZERO;
    float t;
    float b;
    float l;
    float r;

    public IBorder(final float pad) {
        this(pad, pad, pad, pad);
    }

    public IBorder(final float tb, final float rl) {
        this(tb, rl, tb, rl);
    }

    public IBorder(final float t, final float rl, final float b) {
        this(t, rl, b, rl);
    }

    public IBorder(final float t, final float r, final float b, final float l) {
        this.t = t;
        this.b = b;
        this.l = l;
        this.r = r;
    }

    public IBorder(final Position edge, final float n) {
        this((edge == Position.Top) ? n : 0.0f, (edge == Position.Right) ? n : 0.0f, (edge == Position.Bottom) ? n : 0.0f, (edge == Position.Left) ? n : 0.0f);
    }

    public IBorder(final IBorder padding) {
        this(padding.t(), padding.r(), padding.b(), padding.l());
    }

    public float t() {
        return this.t;
    }

    public float b() {
        return this.b;
    }

    public float l() {
        return this.l;
    }

    public float r() {
        return this.r;
    }

    public float t(final float n) {
        return this.t = n;
    }

    public float b(final float n) {
        return this.b = n;
    }

    public float l(final float n) {
        return this.l = n;
    }

    public float r(final float n) {
        return this.r = n;
    }

    public boolean isNonZero() {
        return this.t != 0.0f || this.r != 0.0f || this.l != 0.0f || this.r != 0.0f;
    }

    @Deprecated
    public IPoint tl() {
        return new IPoint(this.l(), this.t());
    }

    @Deprecated
    public IPoint tr() {
        return new IPoint(this.r(), this.t());
    }

    @Deprecated
    public IPoint bl() {
        return new IPoint(this.l(), this.b());
    }

    @Deprecated
    public IPoint br() {
        return new IPoint(this.r(), this.b());
    }

    public IBorder add(final IBorder o) {
        return new IBorder(this.t() + o.t(), this.r() + o.r(), this.b() + o.b(), this.l() + o.l());
    }

    @Override
    public String toString() {
        return this.t() + "-" + this.r() + "-" + this.b() + "-" + this.l();
    }

    static {
        ZERO = new IBorder(0.0f);
    }
}
