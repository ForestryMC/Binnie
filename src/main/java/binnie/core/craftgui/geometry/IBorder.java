package binnie.core.craftgui.geometry;

public class IBorder {
    public static IBorder ZERO = new IBorder(0.0f);

    protected float t;
    protected float b;
    protected float l;
    protected float r;

    public IBorder(float pad) {
        this(pad, pad, pad, pad);
    }

    public IBorder(float tb, float rl) {
        this(tb, rl, tb, rl);
    }

    public IBorder(float t, float rl, float b) {
        this(t, rl, b, rl);
    }

    public IBorder(float t, float r, float b, float l) {
        this.t = t;
        this.b = b;
        this.l = l;
        this.r = r;
    }

    public IBorder(Position edge, float n) {
        this(
                (edge == Position.TOP) ? n : 0.0f,
                (edge == Position.RIGHT) ? n : 0.0f,
                (edge == Position.BOTTOM) ? n : 0.0f,
                (edge == Position.LEFT) ? n : 0.0f);
    }

    public IBorder(IBorder padding) {
        this(padding.t(), padding.r(), padding.b(), padding.l());
    }

    public float t() {
        return t;
    }

    public float b() {
        return b;
    }

    public float l() {
        return l;
    }

    public float r() {
        return r;
    }

    public float t(float n) {
        return t = n;
    }

    public float b(float n) {
        return b = n;
    }

    public float l(float n) {
        return l = n;
    }

    public float r(float n) {
        return r = n;
    }

    public boolean isNonZero() {
        return t != 0.0f || r != 0.0f || l != 0.0f || b != 0.0f;
    }

    @Deprecated
    public IPoint tl() {
        return new IPoint(l(), t());
    }

    @Deprecated
    public IPoint tr() {
        return new IPoint(r(), t());
    }

    @Deprecated
    public IPoint bl() {
        return new IPoint(l(), b());
    }

    @Deprecated
    public IPoint br() {
        return new IPoint(r(), b());
    }

    public IBorder add(IBorder o) {
        return new IBorder(t() + o.t(), r() + o.r(), b() + o.b(), l() + o.l());
    }

    @Override
    public String toString() {
        return t() + "-" + r() + "-" + b() + "-" + l();
    }
}
