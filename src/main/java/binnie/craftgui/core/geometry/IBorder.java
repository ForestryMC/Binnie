package binnie.craftgui.core.geometry;

public class IBorder {
	public static final IBorder ZERO = new IBorder(0);
	int t;
	int b;
	int l;
	int r;

	public IBorder(final int pad) {
		this(pad, pad, pad, pad);
	}

	public IBorder(final int tb, final int rl) {
		this(tb, rl, tb, rl);
	}

	public IBorder(final int t, final int rl, final int b) {
		this(t, rl, b, rl);
	}

	public IBorder(final int t, final int r, final int b, final int l) {
		this.t = t;
		this.b = b;
		this.l = l;
		this.r = r;
	}

	public IBorder(final Position edge, final int n) {
		this((edge == Position.Top) ? n : 0, (edge == Position.Right) ? n : 0, (edge == Position.Bottom) ? n : 0, (edge == Position.Left) ? n : 0);
	}

	public IBorder(final IBorder padding) {
		this(padding.t(), padding.r(), padding.b(), padding.l());
	}

	public int t() {
		return this.t;
	}

	public int b() {
		return this.b;
	}

	public int l() {
		return this.l;
	}

	public int r() {
		return this.r;
	}

	public int t(final int n) {
		return this.t = n;
	}

	public int b(final int n) {
		return this.b = n;
	}

	public int l(final int n) {
		return this.l = n;
	}

	public int r(final int n) {
		return this.r = n;
	}

	public boolean isNonZero() {
		return this.t != 0.0f || this.b != 0.0f || this.l != 0.0f || this.r != 0.0f;
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

}
