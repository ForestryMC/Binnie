package binnie.core.craftgui.controls.tab;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Position;
import java.util.Collection;

public class ControlTabBar<T> extends Control implements IControlValue<T> {
    protected T value;
    protected Position position;

    public ControlTabBar(IWidget parent, float x, float y, float width, float height, Position position) {
        super(parent, x, y, width, height);
        this.position = position;
        addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        setValue((T) event.getValue());
                    }
                }.setOrigin(EventHandler.Origin.DirectChild, this));
    }

    public ControlTab<T> createTab(float x, float y, float w, float h, T value) {
        return new ControlTab<>(this, x, y, w, h, value);
    }

    public void setValues(Collection<T> values) {
        int i = 0;
        while (i < getWidgets().size()) {
            deleteChild(getWidgets().get(0));
        }

        float length = values.size();
        int tabDimension = (int) (getSize().y() / length);
        if (position == Position.TOP || position == Position.BOTTOM) {
            tabDimension = (int) (getSize().x() / length);
        }

        int j = 0;
        for (T value : values) {
            if (position == Position.TOP || position == Position.BOTTOM) {
                createTab(j * tabDimension, 0.0f, tabDimension, getSize().y(), value);
            } else {
                createTab(0.0f, j * tabDimension, getSize().x(), tabDimension, value);
            }
            j++;
        }

        if (value == null && !values.isEmpty()) {
            setValue(values.iterator().next());
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        boolean change = this.value != value;
        this.value = value;
        if (change) {
            callEvent(new EventValueChanged<Object>(this, value));
        }
    }

    public Position getDirection() {
        return position;
    }
}
