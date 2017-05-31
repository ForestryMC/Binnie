package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlEnumButton<T> extends ControlButton implements IControlValue<T> {
	public static final String eventEnumChanged = "eventEnumButtonChanged";
	private T currentSelection;
	private List<T> enumConstants;

	public ControlEnumButton(final IWidget parent, final int x, final int y, final int width, final int height, final T[] values) {
		super(parent, x, y, width, height, "");
		Preconditions.checkArgument(values.length > 0, "Tried to create ControlEnumButton with no values.");
		this.enumConstants = new ArrayList<>();
		Collections.addAll(this.enumConstants, values);
		this.currentSelection = values[0];
	}

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
	public T getValue() {
		return this.currentSelection;
	}

	@Override
	public void setValue(final T selection) {
		if (this.currentSelection != selection) {
			this.currentSelection = selection;
			this.callEvent(new EventValueChanged<Object>(this, this.getValue()));
		}
	}
}
