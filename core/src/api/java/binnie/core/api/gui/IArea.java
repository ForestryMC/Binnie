package binnie.core.api.gui;

public interface IArea {
	IPoint pos();

	IPoint getPosition();

	void setPosition(IPoint position);

	IPoint size();

	IPoint getSize();

	void setSize(IPoint size);

	boolean contains(IPoint position);

	int xPos();

	int yPos();

	int width();

	int height();

	void setXPos(int xPos);

	void setYPos(int yPos);

	void setWidth(int width);

	void setHeight(int height);

	IArea inset(IBorder border);

	IArea outset(int outset);

	IArea outset(IBorder border);

	IArea inset(int inset);

	IArea shift(int dx, int f);
}
