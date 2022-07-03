package binnie.core.craftgui.controls.core;

import binnie.core.craftgui.IWidget;

public interface IControlValue<T> extends IWidget {
    T getValue();

    void setValue(T value);
}
