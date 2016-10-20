package binnie.craftgui.controls;

public interface IControlSelection<T> {
    T getSelectedValue();

    void setSelectedValue(final T p0);

    boolean isSelected(final IControlSelectionOption<T> p0);
}
