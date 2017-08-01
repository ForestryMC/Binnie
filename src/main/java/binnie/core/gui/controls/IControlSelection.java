package binnie.core.gui.controls;

public interface IControlSelection<T> {
	T getSelectedValue();

	void setSelectedValue(T value);

	boolean isSelected(IControlSelectionOption<T> option);
}
