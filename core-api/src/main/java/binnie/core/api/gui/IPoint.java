package binnie.core.api.gui;

public interface IPoint {

	IPoint sub(IPoint point);

	IPoint sub(int xPos, int yPos);

	IPoint add(IPoint other);

	IPoint add(int xPos, int yPos);

	IPoint copy();

	int xPos();

	int yPos();

	boolean equals(IPoint other);
}
