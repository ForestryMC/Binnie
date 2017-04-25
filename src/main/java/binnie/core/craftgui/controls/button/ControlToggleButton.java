package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventToggleButtonClicked;

// TODO unused class?
public class ControlToggleButton extends ControlButton {
	boolean toggled;

	@Override
	public void onMouseClick(EventMouse.Down event) {
		callEvent(new EventToggleButtonClicked(this, toggled));
	}

	public ControlToggleButton(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height);
	}
}
