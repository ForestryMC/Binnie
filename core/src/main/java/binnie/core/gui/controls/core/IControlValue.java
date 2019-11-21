package binnie.core.gui.controls.core;

import binnie.core.api.gui.IWidget;

import javax.annotation.Nullable;

public interface IControlValue<T> extends IWidget {
	@Nullable
	T getValue();

	void setValue(@Nullable final T value);
}
