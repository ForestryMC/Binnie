package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel {
    public boolean stayOpenOnChildClick;

    public ControlDropDownMenu(final IWidget parent, final float x, final float y, final float width, final float height) {
        super(parent, x, y, width, 2.0f, MinecraftGUI.PanelType.Gray);
        this.stayOpenOnChildClick = false;
        this.addAttribute(Attribute.CanFocus);
    }
}
