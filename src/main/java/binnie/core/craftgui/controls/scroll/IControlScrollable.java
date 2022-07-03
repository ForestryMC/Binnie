package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.IWidget;

public interface IControlScrollable extends IWidget {
    float getPercentageShown();

    float getPercentageIndex();

    void movePercentage(float percentage);

    void setPercentageIndex(float index);

    float getMovementRange();
}
