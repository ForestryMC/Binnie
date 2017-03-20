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

	public IArea(final int xywh) {
		this(xywh, xywh, xywh, xywh);
	}

	public IArea(final int xy, final int wh) {
		this(xy, xy, wh, wh);
	}

	public IArea(final int x, final int y, final int wh) {
		this(x, y, wh, wh);
	}

	public IArea(final int x, final int y, final int w, final int h) {
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

	public int xPos() {
		return this.pos().x();
	}

	public int yPos() {
		return this.pos().y();
	}

	public int width() {
		return this.size().x();
	}

	public int height() {
		return this.size().y();
	}
	
	public void setXPos(final int xPos) {
		this.pos = new IPoint(xPos, this.pos.y());
	}

	public void setYPos(final int yPos) {
		this.pos = new IPoint(this.pos.x(), yPos);
	}

	public void setWidth(final int width) {
		this.size = new IPoint(width, this.size.y());
	}

	public void setHeight(final int height) {
		this.size = new IPoint(this.size.x(), height);
	}

	public IArea inset(final IBorder border) {
		return new IArea(this.xPos() + border.l(), this.yPos() + border.t(), this.width() - border.l() - border.r(), this.height() - border.t() - border.b());
	}

	public IArea outset(final int outset) {
		return this.outset(new IBorder(outset));
	}

	public IArea outset(final IBorder border) {
		return new IArea(this.xPos() - border.l(), this.yPos() - border.t(), this.width() + border.l() + border.r(), this.height() + border.t() + border.b());
	}

	public IArea inset(final int inset) {
		return this.inset(new IBorder(inset));
	}

	@Override
	public String toString() {
		return this.width() + "x" + this.height() + "@" + this.xPos() + "," + this.yPos();
	}

	public IArea shift(final int dx, final int f) {
		return new IArea(this.xPos() + dx, this.yPos() + f, this.width(), this.height());
	}
}
