package binnie.core.craftgui.controls.core;

import java.util.Collection;

public interface IControlValues<T> extends IControlValue<T> {
    Collection<T> getValues();

    void setValues(Collection<T> values);
}
