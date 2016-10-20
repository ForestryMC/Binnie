package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.button.ControlButton;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;

public abstract class ControlDropdownButton extends ControlButton {
    public ControlDropdownButton(final IWidget parent, final float x, final float y, final float width, final float height, final String text) {
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
