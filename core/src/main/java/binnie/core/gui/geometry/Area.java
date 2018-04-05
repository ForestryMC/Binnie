package binnie.core.gui.geometry;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IBorder;
import binnie.core.api.gui.IPoint;

public final class Area implements IArea {
	private IPoint pos;
	private IPoint size;

	public Area(final Area area) {
		this(area.pos().xPos(), area.pos().yPos(), area.size().xPos(), area.size().yPos());
	}

	public Area(final IPoint pos, final IPoint size) {
		this(pos.xPos(), pos.yPos(), size.xPos(), size.yPos());
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
		this.pos = new Point(x, y);
		this.size = new Point(w, h);
	}

	@Override
	public IPoint pos() {
		return this.pos;
	}

	@Override
	public IPoint getPosition() {
		return this.pos;
	}

	@Override
	public void setPosition(final IPoint position) {
		this.pos = position.copy();
	}

	@Override
	public IPoint size() {
		return this.size;
	}

	@Override
	public IPoint getSize() {
		return this.size;
	}

	@Override
	public void setSize(final IPoint size) {
		this.size = size.copy();
	}

	@Override
	public boolean contains(final IPoint position) {
		return position.xPos() >= this.pos().xPos() && position.yPos() >= this.pos.yPos() && position.xPos() <= this.pos().xPos() + this.size().xPos() && position.yPos() <= this.pos().yPos() + this.size().yPos();
	}

	@Override
	public int xPos() {
		return this.pos().xPos();
	}

	@Override
	public int yPos() {
		return this.pos().yPos();
	}

	@Override
	public int width() {
		return this.size().xPos();
	}

	@Override
	public int height() {
		return this.size().yPos();
	}

	@Override
	public void setXPos(final int xPos) {
		this.pos = new Point(xPos, this.pos.yPos());
	}

	@Override
	public void setYPos(final int yPos) {
		this.pos = new Point(this.pos.xPos(), yPos);
	}

	@Override
	public void setWidth(final int width) {
		this.size = new Point(width, this.size.yPos());
	}

	@Override
	public void setHeight(final int height) {
		this.size = new Point(this.size.xPos(), height);
	}

	@Override
	public IArea inset(final IBorder border) {
		return new Area(this.xPos() + border.getLeft(), this.yPos() + border.getTop(), this.width() - border.getLeft() - border.getRight(), this.height() - border.getTop() - border.getBottom());
	}

	@Override
	public IArea outset(final int outset) {
		return this.outset(new Border(outset));
	}

	@Override
	public IArea outset(final IBorder border) {
		return new Area(this.xPos() - border.getLeft(), this.yPos() - border.getTop(), this.width() + border.getLeft() + border.getRight(), this.height() + border.getTop() + border.getBottom());
	}

	@Override
	public IArea inset(final int inset) {
		return this.inset(new Border(inset));
	}

	@Override
	public String toString() {
		return this.width() + "x" + this.height() + '@' + this.xPos() + ',' + this.yPos();
	}

	@Override
	public IArea shift(final int dx, final int f) {
		return new Area(this.xPos() + dx, this.yPos() + f, this.width(), this.height());
	}
}
