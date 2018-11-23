package binnie.core.gui.controls.listbox;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Point;
import binnie.core.util.IValidator;

public class ControlList<T> extends Control implements IControlValue<T> {
	private final ControlListBox<T> parent;
	private final Map<T, IWidget> allOptions;
	private final Map<T, IWidget> optionWidgets;
	@Nullable
	private
	IValidator<IWidget> validator;
	@Nullable
	private final T defaultValue;
	@Nullable
	private T value;

	protected ControlList(ControlListBox<T> parent, int x, int y, int w, int h, @Nullable T defaultValue) {
		super(parent, x, y, w, h);
		this.value = defaultValue;
		this.defaultValue = defaultValue;
		this.allOptions = new LinkedHashMap<>();
		this.optionWidgets = new LinkedHashMap<>();
		this.parent = parent;
	}

	@Override
	@Nullable
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(@Nullable T value) {
		if (value == this.value) {
			return;
		}
		this.value = value;
		if (this.optionWidgets.containsKey(value)) {
			IWidget child = this.optionWidgets.get(value);
			this.parent.ensureVisible(child.getYPos(), child.getYPos() + child.getHeight(), this.getHeight());
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
		for (Map.Entry<T, IWidget> entry : this.allOptions.entrySet()) {
			if (this.isValidOption(entry.getValue())) {
				entry.getValue().show();
				this.optionWidgets.put(entry.getKey(), entry.getValue());
				entry.getValue().setPosition(new Point(0, height));
				height += entry.getValue().getSize().yPos();
			} else {
				entry.getValue().hide();
			}
		}
		this.setValue(this.getValue());
		this.setSize(new Point(this.getSize().xPos(), height));
	}

	public Collection<T> getOptions() {
		return this.optionWidgets.keySet();
	}

	public void setOptions(Collection<T> options) {
		this.deleteAllChildren();
		this.allOptions.clear();
		for (T option : options) {
			IWidget optionWidget = this.parent.createOption(option, 0);
			this.allOptions.put(option, optionWidget);
		}
		this.filterOptions();
	}

	public int getIndexOf(T value) {
		int index = 0;
		for (T option : this.getOptions()) {
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

	public void setIndex(int currentIndex) {
		int index = 0;
		for (T option : this.getOptions()) {
			if (index == currentIndex) {
				this.setValue(option);
				return;
			}
			++index;
		}
		this.setValue(defaultValue);
	}

	private boolean isValidOption(IWidget widget) {
		return this.validator == null || this.validator.isValid(widget);
	}

	public void setValidator(IValidator<IWidget> validator) {
		if (this.validator != validator) {
			this.validator = validator;
			this.filterOptions();
		}
	}

	@Nullable
	@Override
	public ControlListBox<T> getParent() {
		return parent;
	}
}
