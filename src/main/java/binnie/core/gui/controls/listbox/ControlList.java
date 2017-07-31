package binnie.core.gui.controls.listbox;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Point;
import binnie.core.util.IValidator;

public class ControlList<T> extends Control implements IControlValue<T> {
	ControlListBox<T> parent;
	Map<T, IWidget> allOptions;
	Map<T, IWidget> optionWidgets;
	boolean creating;
	@Nullable
	IValidator<IWidget> validator;
	@Nullable
	private T defaultValue;
	@Nullable
	private T value;

	protected ControlList(final ControlListBox<T> parent, final int x, final int y, final int w, final int h, @Nullable final T defaultValue) {
		super(parent, x, y, w, h);
		this.value = defaultValue;
		this.defaultValue = defaultValue;
		this.allOptions = new LinkedHashMap<>();
		this.optionWidgets = new LinkedHashMap<>();
		this.creating = false;
		this.parent = parent;
	}

	@Override
	@Nullable
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(@Nullable final T value) {
		if (value == this.value) {
			return;
		}
		this.value = value;
		if (this.optionWidgets.containsKey(value)) {
			final IWidget child = this.optionWidgets.get(value);
			this.parent.ensureVisible(child.getYPos(), child.getYPos() + child.height(), this.height());
		}
		this.parent.callEvent(new EventValueChanged<Object>(this.parent, value));
	}

	@Nullable
	public T getDefaultValue() {
		return defaultValue;
	}

	public void filterOptions() {
		int height = 0;
		this.optionWidgets.clear();
		for (final Map.Entry<T, IWidget> entry : this.allOptions.entrySet()) {
			if (this.isValidOption(entry.getValue())) {
				entry.getValue().show();
				this.optionWidgets.put(entry.getKey(), entry.getValue());
				entry.getValue().setPosition(new Point(0, height));
				height += entry.getValue().getSize().y();
			} else {
				entry.getValue().hide();
			}
		}
		this.creating = true;
		this.setValue(this.getValue());
		this.setSize(new Point(this.getSize().x(), height));
	}

	public Collection<T> getOptions() {
		return this.optionWidgets.keySet();
	}

	public void setOptions(final Collection<T> options) {
		this.deleteAllChildren();
		this.allOptions.clear();
		for (final T option : options) {
			final IWidget optionWidget = this.parent.createOption(option, 0);
			this.allOptions.put(option, optionWidget);
		}
		this.filterOptions();
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
		this.setValue(defaultValue);
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
