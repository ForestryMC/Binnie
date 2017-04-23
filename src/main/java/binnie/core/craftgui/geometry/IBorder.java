// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.geometry;

public class IBorder
{
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

	public float t(final float n) {
		return t = n;
	}

	public float b(final float n) {
		return b = n;
	}

	public float l(final float n) {
		return l = n;
	}

	public float r(final float n) {
		return r = n;
	}

	public boolean isNonZero() {
		return t != 0.0f || r != 0.0f || l != 0.0f || r != 0.0f;
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

	public IBorder add(final IBorder o) {
		return new IBorder(t() + o.t(), r() + o.r(), b() + o.b(), l() + o.l());
	}

	@Override
	public String toString() {
		return t() + "-" + r() + "-" + b() + "-" + l();
	}

	static {
		ZERO = new IBorder(0.0f);
	}
}
