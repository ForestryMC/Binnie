package binnie.core.craftgui.controls.core;

import binnie.core.craftgui.IWidget;

import javax.annotation.Nullable;

public interface IControlValue<T> extends IWidget {
	@Nullable
	T getValue();

	void setValue(@Nullable final T value);
}
