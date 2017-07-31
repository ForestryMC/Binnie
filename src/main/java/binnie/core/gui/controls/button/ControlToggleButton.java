package binnie.core.gui.controls.button;

import binnie.core.gui.IWidget;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventToggleButtonClicked;

public class ControlToggleButton extends ControlButton {
	boolean toggled;

	public ControlToggleButton(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height);
	}

	@Override
	public void onMouseClick(final EventMouse.Down event) {
		this.callEvent(new EventToggleButtonClicked(this, this.toggled));
	}
}
