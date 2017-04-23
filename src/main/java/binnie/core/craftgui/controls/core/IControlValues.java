// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.core;

import java.util.Collection;

public interface IControlValues<T> extends IControlValue<T>
{
	Collection<T> getValues();

	void setValues(final Collection<T> p0);
}
