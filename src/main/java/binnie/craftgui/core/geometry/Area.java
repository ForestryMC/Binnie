package binnie.craftgui.core.geometry;

public class Area {
	private Point pos;
	private Point size;

	public Area(final Area area) {
		this(area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
	}

	public Area(final Point pos, final Point size) {
		this(pos.x(), pos.y(), size.x(), size.y());
	}

	public Area(final int xywh) {
		this(xywh, xywh, xywh, xywh);
	}

	public Area(final int xy, final int wh) {
		this(xy, xy, wh, wh);
	}

	public Area(final int x, final int y, final int wh) {
		this(x, y, wh, wh);
	}

	public Area(final int x, final int y, final int w, final int h) {
		this.setPosition(new Point(x, y));
		this.setSize(new Point(w, h));
	}

	public Point pos() {
		return this.pos;
	}

	public Point getPosition() {
		return this.pos;
	}

	public void setPosition(final Point position) {
		this.pos = position.copy();
	}

	public Point size() {
		return this.size;
	}

	public Point getSize() {
		return this.size;
	}

	public void setSize(final Point size) {
		this.size = size.copy();
	}

	public boolean contains(final Point position) {
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
		this.pos = new Point(xPos, this.pos.y());
	}

	public void setYPos(final int yPos) {
		this.pos = new Point(this.pos.x(), yPos);
	}

	public void setWidth(final int width) {
		this.size = new Point(width, this.size.y());
	}

	public void setHeight(final int height) {
		this.size = new Point(this.size.x(), height);
	}

	public Area inset(final Border border) {
		return new Area(this.xPos() + border.l(), this.yPos() + border.t(), this.width() - border.l() - border.r(), this.height() - border.t() - border.b());
	}

	public Area outset(final int outset) {
		return this.outset(new Border(outset));
	}

	public Area outset(final Border border) {
		return new Area(this.xPos() - border.l(), this.yPos() - border.t(), this.width() + border.l() + border.r(), this.height() + border.t() + border.b());
	}

	public Area inset(final int inset) {
		return this.inset(new Border(inset));
	}

	@Override
	public String toString() {
		return this.width() + "x" + this.height() + "@" + this.xPos() + "," + this.yPos();
	}

	public Area shift(final int dx, final int f) {
		return new Area(this.xPos() + dx, this.yPos() + f, this.width(), this.height());
	}
}
