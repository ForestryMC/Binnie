package binnie.core.gui.controls.tab;

import com.google.common.collect.Iterables;

import java.util.Collection;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Position;

public class ControlTabBar<T> extends Control implements IControlValue<T> {
	private T value;
	private Position position;

	public ControlTabBar(final IWidget parent, final int x, final int y, final int width, final int height, final Position position, final Collection<T> values) {
		this(parent, x, y, width, height, position, Iterables.get(values, 0));
		setValues(values);
	}

	public ControlTabBar(final IWidget parent, final int x, final int y, final int width, final int height, final Position position, final T value) {
		super(parent, x, y, width, height);
		this.value = value;
		this.position = position;
		this.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				ControlTabBar.this.setValue((T) event.getValue());
			}
		}.setOrigin(EventHandler.Origin.DIRECT_CHILD, this));
	}

	private void setValues(final Collection<T> values) {
		deleteAllChildren();
		final float length = values.size();
		int tabDimension = (int) (this.getSize().yPos() / length);
		if (this.position == Position.Top || this.position == Position.BOTTOM) {
			tabDimension = (int) (this.getSize().xPos() / length);
		}
		int j = 0;
		for (final T value : values) {
			if (this.position == Position.Top || this.position == Position.BOTTOM) {
				final IWidget tab = this.createTab(j * tabDimension, 0, tabDimension, this.getSize().yPos(), value);
			} else {
				final IWidget tab = this.createTab(0, j * tabDimension, this.getSize().xPos(), tabDimension, value);
			}
			++j;
		}
	}

	public ControlTab<T> createTab(final int x, final int y, final int w, final int h, final T value) {
		return new ControlTab<>(this, x, y, w, h, value);
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
