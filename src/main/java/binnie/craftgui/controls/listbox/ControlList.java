package binnie.craftgui.controls.listbox;

import binnie.core.util.IValidator;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventValueChanged;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControlList<T> extends Control implements IControlValue<T> {
    ControlListBox<T> parent;
    T value;
    Map<T, IWidget> allOptions;
    Map<T, IWidget> optionWidgets;
    boolean creating;
    IValidator<IWidget> validator;

    protected ControlList(final ControlListBox<T> parent, final float x, final float y, final float w, final float h) {
        super(parent, x, y, w, h);
        this.value = null;
        this.allOptions = new LinkedHashMap<T, IWidget>();
        this.optionWidgets = new LinkedHashMap<T, IWidget>();
        this.creating = false;
        this.parent = parent;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(final T value) {
        if (value == this.value) {
            return;
        }
        this.value = value;
        if (value != null && this.optionWidgets.containsKey(value)) {
            final IWidget child = this.optionWidgets.get(value);
            this.parent.ensureVisible(child.y(), child.y() + child.h(), this.h());
        }
        this.getParent().callEvent(new EventValueChanged<Object>(this.getParent(), value));
    }

    public void setOptions(final Collection<T> options) {
        this.deleteAllChildren();
        this.allOptions.clear();
        int i = 0;
        for (final T option : options) {
            final IWidget optionWidget = ((ControlListBox) this.getParent()).createOption(option, 0);
            if (optionWidget != null) {
                this.allOptions.put(option, optionWidget);
            }
            ++i;
        }
        this.filterOptions();
    }

    public void filterOptions() {
        int height = 0;
        this.optionWidgets.clear();
        for (final Map.Entry<T, IWidget> entry : this.allOptions.entrySet()) {
            if (this.isValidOption(entry.getValue())) {
                entry.getValue().show();
                this.optionWidgets.put(entry.getKey(), entry.getValue());
                entry.getValue().setPosition(new IPoint(0.0f, height));
                height += (int) entry.getValue().getSize().y();
            } else {
                entry.getValue().hide();
            }
        }
        this.creating = true;
        this.setValue(this.getValue());
        this.setSize(new IPoint(this.getSize().x(), height));
    }

    public Collection<T> getOptions() {
        return this.optionWidgets.keySet();
    }

    public Collection<T> getAllOptions() {
        return this.allOptions.keySet();
    }

    public int getIndexOf(final T value) {
        int index = 0;
        for (final T option : this.getOptions()) {
            if (option.equals(value)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public int getCurrentIndex() {
        return this.getIndexOf(this.getValue());
    }

    public void setIndex(final int currentIndex) {
        int index = 0;
        for (final T option : this.getOptions()) {
            if (index == currentIndex) {
                this.setValue(option);
                return;
            }
            ++index;
        }
        this.setValue(null);
    }

    private boolean isValidOption(final IWidget widget) {
        return this.validator == null || this.validator.isValid(widget);
    }

    public void setValidator(final IValidator<IWidget> validator) {
        if (this.validator != validator) {
            this.validator = validator;
            this.filterOptions();
        }
    }
}
