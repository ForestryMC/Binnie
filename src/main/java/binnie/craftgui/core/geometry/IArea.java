package binnie.craftgui.core.geometry;

public class IArea {
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
        this.setPosition(new IPoint(x, y));
        this.setSize(new IPoint(w, h));
    }

    public IPoint pos() {
        return this.pos;
    }

    public IPoint getPosition() {
        return this.pos;
    }

    public void setPosition(final IPoint position) {
        this.pos = position.copy();
    }

    public IPoint size() {
        return this.size;
    }

    public IPoint getSize() {
        return this.size;
    }

    public void setSize(final IPoint size) {
        this.size = size.copy();
    }

    public boolean contains(final IPoint position) {
        return position.x() >= this.pos().x() && position.y() >= this.pos.y() && position.x() <= this.pos().x() + this.size().x() && position.y() <= this.pos().y() + this.size().y();
    }

    public float x() {
        return this.pos().x();
    }

    public float y() {
        return this.pos().y();
    }

    public float w() {
        return this.size().x();
    }

    public float h() {
        return this.size().y();
    }

    public float x(final float n) {
        return this.pos.x(n);
    }

    public float y(final float n) {
        return this.pos.y(n);
    }

    public float w(final float n) {
        return this.size.x(n);
    }

    public float h(final float n) {
        return this.size.y(n);
    }

    public IArea inset(final IBorder border) {
        return new IArea(this.x() + border.l(), this.y() + border.t(), this.w() - border.l() - border.r(), this.h() - border.t() - border.b());
    }

    public IArea outset(final int outset) {
        return this.outset(new IBorder(outset));
    }

    public IArea outset(final IBorder border) {
        return new IArea(this.x() - border.l(), this.y() - border.t(), this.w() + border.l() + border.r(), this.h() + border.t() + border.b());
    }

    public IArea inset(final int inset) {
        return this.inset(new IBorder(inset));
    }

    @Override
    public String toString() {
        return this.w() + "x" + this.h() + "@" + this.x() + "," + this.y();
    }

    public IArea shift(final float dx, final float f) {
        return new IArea(this.x() + dx, this.y() + f, this.w(), this.h());
    }
}
