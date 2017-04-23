// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.tab;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Position;

import java.util.Collection;

public class ControlTabBar<T> extends Control implements IControlValue<T>
{
	T value;
	Position position;

	public ControlTabBar(final IWidget parent, final float x, final float y, final float width, final float height, final Position position) {
		super(parent, x, y, width, height);
		this.position = position;
		addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				setValue((T) event.getValue());
			}
		}.setOrigin(EventHandler.Origin.DirectChild, this));
	}

	public void setValues(final Collection<T> values) {
		final int i = 0;
		while (i < getWidgets().size()) {
			deleteChild(getWidgets().get(0));
		}
		final float length = values.size();
		int tabDimension = (int) (getSize().y() / length);
		if (position == Position.Top || position == Position.Bottom) {
			tabDimension = (int) (getSize().x() / length);
		}
		int j = 0;
		for (final T value : values) {
			if (position == Position.Top || position == Position.Bottom) {
				final IWidget tab = createTab(j * tabDimension, 0.0f, tabDimension, getSize().y(), value);
			}
			else {
				final IWidget tab = createTab(0.0f, j * tabDimension, getSize().x(), tabDimension, value);
			}
			++j;
		}
		if (value == null && !values.isEmpty()) {
			setValue(values.iterator().next());
		}
	}

	public ControlTab<T> createTab(final float x, final float y, final float w, final float h, final T value) {
		return new ControlTab<T>(this, x, y, w, h, value);
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(final T value) {
		final boolean change = this.value != value;
		this.value = value;
		if (change) {
			callEvent(new EventValueChanged<Object>(this, value));
		}
	}

	public Position getDirection() {
		return position;
	}
}
