package binnie.extratrees.kitchen.craftgui;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.button.ControlButton;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.minecraft.Window;

public abstract class ControlDropdownButton extends ControlButton {
	public ControlDropdownButton(final IWidget parent, final int x, final int y, final int width, final int height, final String text) {
		super(parent, x, y, width, height, text);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				ControlDropDownMenu menu = ControlDropdownButton.this.getWidget(ControlDropDownMenu.class);
				ControlDropdownButton.this.deleteChild(ControlDropdownButton.this.getWidget(ControlDropDownMenu.class));
				if (ControlDropdownButton.this.getWidget(ControlDropDownMenu.class) == null) {
					menu = ControlDropdownButton.this.createDropDownMenu();
					Window.get(ControlDropdownButton.this.getWidget()).setFocusedWidget(menu);
				} else {
					ControlDropdownButton.this.deleteChild(ControlDropdownButton.this.getWidget(ControlDropDownMenu.class));
				}
			}
		});
	}

	public abstract ControlDropDownMenu createDropDownMenu();
}
