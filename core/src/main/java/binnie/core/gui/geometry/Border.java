package binnie.core.gui.geometry;

import java.awt.Point;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IBorder;

public class Border implements IBorder {
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

	public Border(final Alignment edge, final int n) {
		this((edge == Alignment.TOP) ? n : 0, (edge == Alignment.RIGHT) ? n : 0, (edge == Alignment.BOTTOM) ? n : 0, (edge == Alignment.LEFT) ? n : 0);
	}

	public Border(final Border padding) {
		this(padding.t(), padding.r(), padding.b(), padding.l());
	}

	@Override
	public int t() {
		return this.t;
	}

	@Override
	public int b() {
		return this.b;
	}

	@Override
	public int l() {
		return this.l;
	}

	@Override
	public int r() {
		return this.r;
	}

	@Override
	public int t(final int n) {
		return this.t = n;
	}

	@Override
	public int b(final int n) {
		return this.b = n;
	}

	@Override
	public int l(final int n) {
		return this.l = n;
	}

	@Override
	public int r(final int n) {
		return this.r = n;
	}

	@Override
	public boolean isNonZero() {
		return this.t != 0.0f || this.b != 0.0f || this.l != 0.0f || this.r != 0.0f;
	}

	@Override
	@Deprecated
	public Point tl() {
		return new Point(this.l(), this.t());
	}

	@Override
	@Deprecated
	public Point tr() {
		return new Point(this.r(), this.t());
	}

	@Override
	@Deprecated
	public Point bl() {
		return new Point(this.l(), this.b());
	}

	@Override
	@Deprecated
	public Point br() {
		return new Point(this.r(), this.b());
	}

	@Override
	public IBorder add(final IBorder o) {
		return new Border(this.t() + o.t(), this.r() + o.r(), this.b() + o.b(), this.l() + o.l());
	}

	@Override
	public String toString() {
		return this.t() + "-" + this.r() + "-" + this.b() + "-" + this.l();
	}
}
