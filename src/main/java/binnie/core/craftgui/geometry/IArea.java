// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.geometry;

public class IArea
{
	private IPoint pos;
	private IPoint size;

	public IArea(final IArea area) {
		this(area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
	}

	public IArea(final IPoint pos, final IPoint size) {
		this(pos.x(), pos.y(), size.x(), size.y());
	}

	public IArea(final float xywh) {
		this(xywh, xywh, xywh, xywh);
	}

	public IArea(final float xy, final float wh) {
		this(xy, xy, wh, wh);
	}

	public IArea(final float x, final float y, final float wh) {
		this(x, y, wh, wh);
	}

	public IArea(final float x, final float y, final float w, final float h) {
		setPosition(new IPoint(x, y));
		setSize(new IPoint(w, h));
	}

	public IPoint pos() {
		return pos;
	}

	public IPoint getPosition() {
		return pos;
	}

	public void setPosition(final IPoint position) {
		pos = position.copy();
	}

	public IPoint size() {
		return size;
	}

	public IPoint getSize() {
		return size;
	}

	public void setSize(final IPoint size) {
		this.size = size.copy();
	}

	public boolean contains(final IPoint position) {
		return position.x() >= pos().x() && position.y() >= pos.y() && position.x() <= pos().x() + size().x() && position.y() <= pos().y() + size().y();
	}

	public float x() {
		return pos().x();
	}

	public float y() {
		return pos().y();
	}

	public float w() {
		return size().x();
	}

	public float h() {
		return size().y();
	}

	public float x(final float n) {
		return pos.x(n);
	}

	public float y(final float n) {
		return pos.y(n);
	}

	public float w(final float n) {
		return size.x(n);
	}

	public float h(final float n) {
		return size.y(n);
	}

	public IArea inset(final IBorder border) {
		return new IArea(x() + border.l(), y() + border.t(), w() - border.l() - border.r(), h() - border.t() - border.b());
	}

	public IArea outset(final int outset) {
		return outset(new IBorder(outset));
	}

	public IArea outset(final IBorder border) {
		return new IArea(x() - border.l(), y() - border.t(), w() + border.l() + border.r(), h() + border.t() + border.b());
	}

	public IArea inset(final int inset) {
		return inset(new IBorder(inset));
	}

	@Override
	public String toString() {
		return w() + "x" + h() + "@" + x() + "," + y();
	}

	public IArea shift(final float dx, final float f) {
		return new IArea(x() + dx, y() + f, w(), h());
	}
}
