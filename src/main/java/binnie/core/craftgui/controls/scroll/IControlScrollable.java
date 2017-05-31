package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.IWidget;

public interface IControlScrollable extends IWidget {
	float getPercentageShown();

	float getPercentageIndex();

	void setPercentageIndex(final float p0);

	void movePercentage(final float p0);

	float getMovementRange();
}
