package binnie.craftgui.controls.page;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.core.IControlValues;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControlPages<T> extends Control implements IControlValues<T>, IControlValue<T> {
	T value;

	@Override
	public boolean isChildVisible(final IWidget child) {
		return child != null && this.value == ((IControlValue) child).getValue();
	}

	public ControlPages(final IWidget parent, final float x, final float y, final float w, final float h) {
		super(parent, x, y, w, h);
		this.value = null;
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
		for (final IWidget child : this.getWidgets()) {
			list.add((T) ((IControlValue) child).getValue());
		}
		return list;
	}

	@Override
	public void setValues(final Collection<T> values) {
	}
}
