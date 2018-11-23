package binnie.core.gui.controls.scroll;

import binnie.core.api.gui.IWidget;

public interface IControlScrollable extends IWidget {
	float getPercentageShown();

	float getPercentageIndex();

	void setPercentageIndex(float p0);

	void movePercentage(float p0);

	float getMovementRange();
}
