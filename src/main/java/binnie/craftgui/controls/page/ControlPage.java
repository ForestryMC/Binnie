package binnie.craftgui.controls.page;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;

public class ControlPage<T> extends Control implements IControlValue<T> {
    T value;

    public ControlPage(final IWidget parent, final T value) {
        this(parent, 0.0f, 0.0f, parent.w(), parent.h(), value);
    }

    public ControlPage(final IWidget parent, final float x, final float y, final float w, final float h, final T value) {
        super(parent, x, y, w, h);
        this.setValue(value);
        if (parent instanceof IControlValue && ((IControlValue) parent).getValue() == null) {
            ((IControlValue) parent).setValue(value);
        }
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(final T value) {
        this.value = value;
    }
}
