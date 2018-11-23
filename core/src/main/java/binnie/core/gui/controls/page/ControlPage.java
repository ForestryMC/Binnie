package binnie.core.gui.controls.page;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;

public class ControlPage<T> extends Control implements IControlValue<T> {
	private T value;

	public ControlPage(IWidget parent, T value) {
		this(parent, 0, 0, parent.getWidth(), parent.getHeight(), value);
	}

	public ControlPage(IWidget parent, int x, int y, int w, int h, T value) {
		super(parent, x, y, w, h);
		this.value = value;
		if (parent instanceof IControlValue) {
			IControlValue controlValue = (IControlValue) parent;
			if (controlValue.getValue() == null) {
				controlValue.setValue(value);
			}
		}
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
}
