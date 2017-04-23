// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.List;

public class ControlEnumButton<T> extends ControlButton implements IControlValue<T>
{
	public static final String eventEnumChanged = "eventEnumButtonChanged";
	private T currentSelection;
	private List<T> enumConstants;

	@Override
	public String getText() {
		return currentSelection.toString();
	}

	@Override
	public void onMouseClick(final EventMouse.Down event) {
		int index = enumConstants.indexOf(currentSelection);
		if (index < enumConstants.size() - 1) {
			++index;
		}
		else {
			index = 0;
		}
		final T newEnum = enumConstants.get(index);
		setValue(newEnum);
	}

	@Override
	public void setValue(final T selection) {
		if (currentSelection != selection) {
			currentSelection = selection;
			callEvent(new EventValueChanged<Object>(this, getValue()));
		}
	}

	public ControlEnumButton(final IWidget parent, final float x, final float y, final float width, final float height, final T[] values) {
		super(parent, x, y, width, height, "");
		enumConstants = new ArrayList<T>();
		for (final T value : values) {
			enumConstants.add(value);
		}
		if (values.length > 0) {
			currentSelection = values[0];
		}
	}

	@Override
	public T getValue() {
		return currentSelection;
	}
}
