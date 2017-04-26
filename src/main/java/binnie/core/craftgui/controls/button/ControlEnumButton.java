package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO unused class?
public class ControlEnumButton<T> extends ControlButton implements IControlValue<T> {
	public static String eventEnumChanged = "eventEnumButtonChanged";
	private T currentSelection;
	private List<T> enumConstants;

	@Override
	public String getText() {
		return currentSelection.toString();
	}

	@Override
	public void onMouseClick(EventMouse.Down event) {
		int index = enumConstants.indexOf(currentSelection);
		if (index < enumConstants.size() - 1) {
			index++;
		} else {
			index = 0;
		}

		T newEnum = enumConstants.get(index);
		setValue(newEnum);
	}

	@Override
	public void setValue(T selection) {
		if (currentSelection != selection) {
			currentSelection = selection;
			callEvent(new EventValueChanged<Object>(this, getValue()));
		}
	}

	public ControlEnumButton(IWidget parent, float x, float y, float width, float height, T[] values) {
		super(parent, x, y, width, height, "");
		enumConstants = new ArrayList<T>();
		Collections.addAll(enumConstants, values);
		if (values.length > 0) {
			currentSelection = values[0];
		}
	}

	@Override
	public T getValue() {
		return currentSelection;
	}
}
