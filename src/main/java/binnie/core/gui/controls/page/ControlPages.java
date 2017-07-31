package binnie.core.gui.controls.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.controls.core.IControlValues;
import binnie.core.gui.events.EventValueChanged;

public class ControlPages<T> extends Control implements IControlValues<T>, IControlValue<T> {
	T value;

	public ControlPages(final IWidget parent, final int x, final int y, final int w, final int h) {
		super(parent, x, y, w, h);
		this.value = null;
	}

	@Override
	public boolean isChildVisible(final IWidget child) {
		return child != null && this.value == ((IControlValue) child).getValue();
	}

	@Override
	public void onAddChild(final IWidget widget) {
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(final T value) {
		if (this.value != value) {
			this.value = value;
			this.callEvent(new EventValueChanged<Object>(this, value));
		}
	}

	@Override
	public Collection<T> getValues() {
		final List<T> list = new ArrayList<>();
		for (final IWidget child : this.getChildren()) {
			list.add((T) ((IControlValue) child).getValue());
		}
		return list;
	}

	@Override
	public void setValues(final Collection<T> values) {
	}
}
