package binnie.core.craftgui.controls;

// TODO unused interface?
public interface IControlSelection<T> {
	T getSelectedValue();

	void setSelectedValue(T p0);

	boolean isSelected(IControlSelectionOption<T> p0);
}
