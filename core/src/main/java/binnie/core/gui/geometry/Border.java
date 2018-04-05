package binnie.core.gui.geometry;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IBorder;

public class Border implements IBorder {
	public static final Border ZERO = new Border(0);

	private int t;
	private int b;
	private int l;
	private int r;

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
		this(padding.getTop(), padding.getRight(), padding.getBottom(), padding.getLeft());
	}

	@Override
	public int getTop() {
		return this.t;
	}

	@Override
	public int getBottom() {
		return this.b;
	}

	@Override
	public int getLeft() {
		return this.l;
	}

	@Override
	public int getRight() {
		return this.r;
	}

	@Override
	public void setTop(final int amount) {
		this.t = amount;
	}

	@Override
	public void setBottom(final int amount) {
		this.b = amount;
	}

	@Override
	public void setLeft(final int amount) {
		this.l = amount;
	}

	@Override
	public void setRight(final int amount) {
		this.r = amount;
	}

	@Override
	public boolean isNonZero() {
		return this.t != 0.0f || this.b != 0.0f || this.l != 0.0f || this.r != 0.0f;
	}

	@Override
	public IBorder add(final IBorder o) {
		return new Border(this.getTop() + o.getTop(), this.getRight() + o.getRight(), this.getBottom() + o.getBottom(), this.getLeft() + o.getLeft());
	}

	@Override
	public String toString() {
		return this.getTop() + "-" + this.getRight() + '-' + this.getBottom() + '-' + this.getLeft();
	}
}
