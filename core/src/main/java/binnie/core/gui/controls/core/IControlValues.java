package binnie.core.gui.controls.core;

import java.util.Collection;

public interface IControlValues<T> extends IControlValue<T> {
	Collection<T> getValues();
}
