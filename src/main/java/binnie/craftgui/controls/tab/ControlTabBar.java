package binnie.craftgui.controls.tab;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventValueChanged;

import java.util.Collection;

public class ControlTabBar<T> extends Control implements IControlValue<T> {
    T value;
    Position position;

    public ControlTabBar(final IWidget parent, final float x, final float y, final float width, final float height, final Position position) {
        super(parent, x, y, width, height);
        this.position = position;
        this.addEventHandler(new EventValueChanged.Handler() {
            @Override
            public void onEvent(final EventValueChanged event) {
                ControlTabBar.this.setValue((T) event.getValue());
            }
        }.setOrigin(EventHandler.Origin.DirectChild, this));
    }

    public void setValues(final Collection<T> values) {
        final int i = 0;
        while (i < this.getWidgets().size()) {
            this.deleteChild(this.getWidgets().get(0));
        }
        final float length = values.size();
        int tabDimension = (int) (this.getSize().y() / length);
        if (this.position == Position.Top || this.position == Position.Bottom) {
            tabDimension = (int) (this.getSize().x() / length);
        }
        int j = 0;
        for (final T value : values) {
            if (this.position == Position.Top || this.position == Position.Bottom) {
                final IWidget tab = this.createTab(j * tabDimension, 0.0f, tabDimension, this.getSize().y(), value);
            } else {
                final IWidget tab = this.createTab(0.0f, j * tabDimension, this.getSize().x(), tabDimension, value);
            }
            ++j;
        }
        if (this.value == null && !values.isEmpty()) {
            this.setValue(values.iterator().next());
        }
    }

    public ControlTab<T> createTab(final float x, final float y, final float w, final float h, final T value) {
        return new ControlTab<T>(this, x, y, w, h, value);
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(final T value) {
        final boolean change = this.value != value;
        this.value = value;
        if (change) {
            this.callEvent(new EventValueChanged<Object>(this, value));
        }
    }

    public Position getDirection() {
        return this.position;
    }
}
