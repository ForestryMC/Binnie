// 
// Decompiled by Procyon v0.5.30
// 

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

public class ControlList<T> extends Control implements IControlValue<T>
{
	ControlListBox<T> parent;
	T value;
	Map<T, IWidget> allOptions;
	Map<T, IWidget> optionWidgets;
	boolean creating;
	IValidator<IWidget> validator;

	protected ControlList(final ControlListBox<T> parent, final float x, final float y, final float w, final float h) {
		super(parent, x, y, w, h);
		value = null;
		allOptions = new LinkedHashMap<T, IWidget>();
		optionWidgets = new LinkedHashMap<T, IWidget>();
		creating = false;
		this.parent = parent;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(final T value) {
		if (value == this.value) {
			return;
		}
		this.value = value;
		if (value != null && optionWidgets.containsKey(value)) {
			final IWidget child = optionWidgets.get(value);
			parent.ensureVisible(child.y(), child.y() + child.h(), h());
		}
		getParent().callEvent(new EventValueChanged<Object>(getParent(), value));
	}

	public void setOptions(final Collection<T> options) {
		deleteAllChildren();
		allOptions.clear();
		int i = 0;
		for (final T option : options) {
			final IWidget optionWidget = ((ControlListBox) getParent()).createOption(option, 0);
			if (optionWidget != null) {
				allOptions.put(option, optionWidget);
			}
			++i;
		}
		filterOptions();
	}

	public void filterOptions() {
		int height = 0;
		optionWidgets.clear();
		for (final Map.Entry<T, IWidget> entry : allOptions.entrySet()) {
			if (isValidOption(entry.getValue())) {
				entry.getValue().show();
				optionWidgets.put(entry.getKey(), entry.getValue());
				entry.getValue().setPosition(new IPoint(0.0f, height));
				height += (int) entry.getValue().getSize().y();
			}
			else {
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

	public Collection<T> getAllOptions() {
		return allOptions.keySet();
	}

	public int getIndexOf(final T value) {
		int index = 0;
		for (final T option : getOptions()) {
			if (option.equals(value)) {
				return index;
			}
			++index;
		}
		return -1;
	}

	public int getCurrentIndex() {
		return getIndexOf(getValue());
	}

	public void setIndex(final int currentIndex) {
		int index = 0;
		for (final T option : getOptions()) {
			if (index == currentIndex) {
				setValue(option);
				return;
			}
			++index;
		}
		setValue(null);
	}

	private boolean isValidOption(final IWidget widget) {
		return validator == null || validator.isValid(widget);
	}

	public void setValidator(final IValidator<IWidget> validator) {
		if (this.validator != validator) {
			this.validator = validator;
			filterOptions();
		}
	}
}
