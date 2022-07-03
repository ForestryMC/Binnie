package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.util.IValidator;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControlList<T> extends Control implements IControlValue<T> {
    protected ControlListBox<T> parent;
    protected T value;
    protected Map<T, IWidget> allOptions = new LinkedHashMap<>();
    protected Map<T, IWidget> optionWidgets = new LinkedHashMap<>();
    protected boolean creating;
    protected IValidator<IWidget> validator;

    protected ControlList(ControlListBox<T> parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        this.parent = parent;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        if (value == this.value) {
            return;
        }

        this.value = value;
        if (value != null && optionWidgets.containsKey(value)) {
            IWidget child = optionWidgets.get(value);
            parent.ensureVisible(child.y(), child.y() + child.h(), h());
        }
        getParent().callEvent(new EventValueChanged<Object>(getParent(), value));
    }

    public void setOptions(Collection<T> options) {
        deleteAllChildren();
        allOptions.clear();

        for (T option : options) {
            IWidget optionWidget = ((ControlListBox) getParent()).createOption(option, 0);
            if (optionWidget != null) {
                allOptions.put(option, optionWidget);
            }
        }
        filterOptions();
    }

    public void filterOptions() {
        int height = 0;
        optionWidgets.clear();

        for (Map.Entry<T, IWidget> entry : allOptions.entrySet()) {
            if (isValidOption(entry.getValue())) {
                entry.getValue().show();
                optionWidgets.put(entry.getKey(), entry.getValue());
                entry.getValue().setPosition(new IPoint(0.0f, height));
                height += (int) entry.getValue().getSize().y();
            } else {
                entry.getValue().hide();
            }
        }

        creating = true;
        setValue(getValue());
        setSize(new IPoint(getSize().x(), height));
    }

    public Collection<T> getOptions() {
        return optionWidgets.keySet();
    }

    public int getIndexOf(T value) {
        int index = 0;
        for (T option : getOptions()) {
            if (option.equals(value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getCurrentIndex() {
        return getIndexOf(getValue());
    }

    public void setIndex(int currentIndex) {
        int index = 0;
        for (T option : getOptions()) {
            if (index == currentIndex) {
                setValue(option);
                return;
            }
            index++;
        }
        setValue(null);
    }

    private boolean isValidOption(IWidget widget) {
        return validator == null || validator.isValid(widget);
    }

    public void setValidator(IValidator<IWidget> validator) {
        if (this.validator != validator) {
            this.validator = validator;
            filterOptions();
        }
    }
}
