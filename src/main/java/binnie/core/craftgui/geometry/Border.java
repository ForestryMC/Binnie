package binnie.core.craftgui.geometry;

public class Border {
	public static final Border ZERO = new Border(0);
	int t;
	int b;
	int l;
	int r;

	public Border(final int pad) {
		this(pad, pad, pad, pad);
	}

	public Border(final int tb, final int rl) {
		this(tb, rl, tb, rl);
	}

	public Border(final int t, final int rl, final int b) {
		this(t, rl, b, rl);
	}

	public Border(final int t, final int r, final int b, final int l) {
		this.t = t;
		this.b = b;
		this.l = l;
		this.r = r;
	}

	public Border(final Position edge, final int n) {
		this((edge == Position.Top) ? n : 0, (edge == Position.RIGHT) ? n : 0, (edge == Position.BOTTOM) ? n : 0, (edge == Position.LEFT) ? n : 0);
	}

	public Border(final Border padding) {
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
	public Point tl() {
		return new Point(this.l(), this.t());
	}

	@Deprecated
	public Point tr() {
		return new Point(this.r(), this.t());
	}

	@Deprecated
	public Point bl() {
		return new Point(this.l(), this.b());
	}

	@Deprecated
	public Point br() {
		return new Point(this.r(), this.b());
	}

	public Border add(final Border o) {
		return new Border(this.t() + o.t(), this.r() + o.r(), this.b() + o.b(), this.l() + o.l());
	}

	@Override
	public String toString() {
		return this.t() + "-" + this.r() + "-" + this.b() + "-" + this.l();
	}
}
