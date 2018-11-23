package binnie.core.gui.controls.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.controls.core.IControlValues;
import binnie.core.gui.events.EventValueChanged;

public class ControlPages<T> extends Control implements IControlValues<T>, IControlValue<T> {
	private T value;

	public ControlPages(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
		this.value = null;
	}

	@Override
	public boolean isChildVisible(IWidget child) {
		return child != null && this.value == ((IControlValue) child).getValue();
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		if (this.value != value) {
			this.value = value;
			this.callEvent(new EventValueChanged<Object>(this, value));
		}
	}

	@Override
	public Collection<T> getValues() {
		List<T> list = new ArrayList<>();
		for (IWidget child : this.getChildren()) {
			list.add((T) ((IControlValue) child).getValue());
		}
		return list;
	}
}
