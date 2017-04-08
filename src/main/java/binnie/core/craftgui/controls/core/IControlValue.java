package binnie.core.craftgui.controls.core;

import javax.annotation.Nullable;

import binnie.core.craftgui.IWidget;

public interface IControlValue<T> extends IWidget {
	@Nullable
	T getValue();

	void setValue(@Nullable final T value);
}
