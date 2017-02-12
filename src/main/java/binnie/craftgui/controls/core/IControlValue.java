package binnie.craftgui.controls.core;

import binnie.craftgui.core.IWidget;

import javax.annotation.Nullable;

public interface IControlValue<T> extends IWidget {
	@Nullable
	T getValue();

	void setValue(@Nullable final T value);
}
