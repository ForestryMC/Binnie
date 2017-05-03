package binnie.extratrees.craftgui.kitchen;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.button.ControlButton;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.minecraft.Window;

// TODO unused class?
public abstract class ControlDropdownButton extends ControlButton {
	public ControlDropdownButton(IWidget parent, float x, float y, float width, float height, String text) {
		super(parent, x, y, width, height, text);
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(EventMouse.Down event) {
				ControlDropDownMenu menu = getWidget(ControlDropDownMenu.class);
				deleteChild(getWidget(ControlDropDownMenu.class));
				if (getWidget(ControlDropDownMenu.class) == null) {
					menu = createDropDownMenu();
					Window.get(getWidget()).setFocusedWidget(menu);
				} else {
					deleteChild(getWidget(ControlDropDownMenu.class));
				}
			}
		});
	}

	public abstract ControlDropDownMenu createDropDownMenu();
}
