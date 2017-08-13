package binnie.core.gui.controls.scroll;

import binnie.core.api.gui.IWidget;

public interface IControlScrollable extends IWidget {
	float getPercentageShown();

	float getPercentageIndex();

	void setPercentageIndex(final float p0);

	void movePercentage(final float p0);

	float getMovementRange();
}
