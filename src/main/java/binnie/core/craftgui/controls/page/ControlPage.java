package binnie.core.craftgui.controls.page;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;

public class ControlPage<T> extends Control implements IControlValue<T> {
	private T value;

	public ControlPage(final IWidget parent, final T value) {
		this(parent, 0, 0, parent.width(), parent.height(), value);
	}

	public ControlPage(final IWidget parent, final int x, final int y, final int w, final int h, final T value) {
		super(parent, x, y, w, h);
		this.value = value;
		if (parent instanceof IControlValue && ((IControlValue) parent).getValue() == null) {
			((IControlValue) parent).setValue(value);
		}
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}
}
