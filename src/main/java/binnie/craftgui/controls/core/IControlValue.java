package binnie.craftgui.controls.core;

import binnie.craftgui.core.IWidget;

public interface IControlValue<T> extends IWidget {
    T getValue();

    void setValue(final T p0);
}
