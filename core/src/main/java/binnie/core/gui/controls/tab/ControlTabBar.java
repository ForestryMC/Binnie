package binnie.core.gui.controls.tab;

import binnie.core.api.gui.events.EventHandlerOrigin;
import com.google.common.collect.Iterables;

import java.util.Collection;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.api.gui.Alignment;

public class ControlTabBar<T> extends Control implements IControlValue<T> {
	public interface ITabCreator<T> {
		ControlTab<T> createTab(final int x, final int y, final int w, final int h, final T value);
	}

	private T value;
	private final Alignment alignment;

	public ControlTabBar(IWidget parent, int x, int y, int width, int height, Alignment alignment, Collection<T> values) {
		this(parent, x, y, width, height, alignment, Iterables.get(values, 0));
		setValues(values, ControlTab::new);
	}

	public ControlTabBar(IWidget parent, int x, int y, int width, int height, Alignment alignment, Collection<T> values, ITabCreator<T> tabCreator) {
		this(parent, x, y, width, height, alignment, Iterables.get(values, 0));
		setValues(values, tabCreator);
	}

	public ControlTabBar(final IWidget parent, final int x, final int y, final int width, final int height, final Alignment alignment, final T value) {
		super(parent, x, y, width, height);
		this.value = value;
		this.alignment = alignment;
		this.addEventHandler(EventValueChanged.class, EventHandlerOrigin.DIRECT_CHILD, this, event -> {
			ControlTabBar.this.setValue((T) event.getValue());
		});
	}

	private void setValues(Collection<T> values, ITabCreator<T> tabCreator) {
		deleteAllChildren();
		final float length = values.size();
		int tabDimension = (int) (this.getSize().yPos() / length);
		if (this.alignment == Alignment.TOP || this.alignment == Alignment.BOTTOM) {
			tabDimension = (int) (this.getSize().xPos() / length);
		}
		int j = 0;
		for (final T value : values) {
			if (this.alignment == Alignment.TOP || this.alignment == Alignment.BOTTOM) {
				ControlTab<T> tab = tabCreator.createTab(j * tabDimension, 0, tabDimension, this.getSize().yPos(), value);
				tab.setTabBar(this);
			} else {
				ControlTab<T> tab = tabCreator.createTab(0, j * tabDimension, this.getSize().xPos(), tabDimension, value);
				tab.setTabBar(this);
			}
			++j;
		}
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

	public Alignment getDirection() {
		return this.alignment;
	}
}
