package binnie.core.craftgui.controls.page;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.core.IControlValues;
import binnie.core.craftgui.events.EventValueChanged;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControlPages<T> extends Control implements IControlValues<T>, IControlValue<T> {
    protected T value;

    public ControlPages(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        value = null;
    }

    @Override
    public void onAddChild(IWidget widget) {
        // ignored
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        if (this.value != value) {
            this.value = value;
            callEvent(new EventValueChanged<Object>(this, value));
        }
    }

    @Override
    public Collection<T> getValues() {
        List<T> list = new ArrayList<>();
        for (IWidget child : getWidgets()) {
            list.add((T) ((IControlValue) child).getValue());
        }
        return list;
    }

    @Override
    public void setValues(Collection<T> values) {
        // ignored
    }

    @Override
    public boolean isChildVisible(IWidget child) {
        return child != null && value == ((IControlValue) child).getValue();
    }
}
