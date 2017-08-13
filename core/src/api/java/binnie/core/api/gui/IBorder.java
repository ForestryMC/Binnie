package binnie.core.api.gui;

public interface IBorder {
	int getTop();

	int getBottom();

	int getLeft();

	int getRight();

	void setTop(int amount);

	void setBottom(int amount);

	void setLeft(int amount);

	void setRight(int amount);

	boolean isNonZero();

	IBorder add(IBorder o);
}
