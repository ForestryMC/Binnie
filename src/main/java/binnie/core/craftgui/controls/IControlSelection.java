// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls;

public interface IControlSelection<T>
{
	T getSelectedValue();

	void setSelectedValue(final T p0);

	boolean isSelected(final IControlSelectionOption<T> p0);
}
