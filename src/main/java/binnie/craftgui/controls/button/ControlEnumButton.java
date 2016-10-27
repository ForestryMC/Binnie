package binnie.craftgui.controls.button;

import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlEnumButton<T> extends ControlButton implements IControlValue<T> {
	public static final String eventEnumChanged = "eventEnumButtonChanged";
	private T currentSelection;
	private List<T> enumConstants;

	@Override
	public String getText() {
		return this.currentSelection.toString();
	}

	@Override
	public void onMouseClick(final EventMouse.Down event) {
		int index = this.enumConstants.indexOf(this.currentSelection);
		if (index < this.enumConstants.size() - 1) {
			++index;
		} else {
			index = 0;
		}
		final T newEnum = this.enumConstants.get(index);
		this.setValue(newEnum);
	}

	@Override
	public void setValue(final T selection) {
		if (this.currentSelection != selection) {
			this.currentSelection = selection;
			this.callEvent(new EventValueChanged<Object>(this, this.getValue()));
		}
	}

	public ControlEnumButton(final IWidget parent, final float x, final float y, final float width, final float height, final T[] values) {
		super(parent, x, y, width, height, "");
		this.enumConstants = new ArrayList<>();
		Collections.addAll(this.enumConstants, values);
		if (values.length > 0) {
			this.currentSelection = values[0];
		}
	}

	@Override
	public T getValue() {
		return this.currentSelection;
	}
}
