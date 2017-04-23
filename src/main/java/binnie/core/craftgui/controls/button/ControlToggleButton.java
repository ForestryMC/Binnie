// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventToggleButtonClicked;

public class ControlToggleButton extends ControlButton
{
	boolean toggled;

	@Override
	public void onMouseClick(final EventMouse.Down event) {
		callEvent(new EventToggleButtonClicked(this, toggled));
	}

	public ControlToggleButton(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height);
	}
}
