package binnie.craftgui.controls.scroll;

import binnie.craftgui.core.IWidget;

public interface IControlScrollable extends IWidget {
    float getPercentageShown();

    float getPercentageIndex();

    void movePercentage(final float p0);

    void setPercentageIndex(final float p0);

    float getMovementRange();
}
