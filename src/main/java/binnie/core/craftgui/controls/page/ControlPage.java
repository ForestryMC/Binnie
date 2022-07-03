package binnie.core.craftgui.controls.page;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;

public class ControlPage<T> extends Control implements IControlValue<T> {
    protected T value;

    public ControlPage(IWidget parent, T value) {
        this(parent, 0.0f, 0.0f, parent.w(), parent.h(), value);
    }

    public ControlPage(IWidget parent, float x, float y, float w, float h, T value) {
        super(parent, x, y, w, h);
        setValue(value);
        if (parent instanceof IControlValue && ((IControlValue) parent).getValue() == null) {
            ((IControlValue) parent).setValue(value);
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
