package binnie.core.gui.controls.core;

import javax.annotation.Nullable;

import binnie.core.api.gui.IWidget;

public interface IControlValue<T> extends IWidget {
	@Nullable
	T getValue();

	void setValue(@Nullable T value);
}
