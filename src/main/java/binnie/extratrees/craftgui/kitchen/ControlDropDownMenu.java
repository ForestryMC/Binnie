package binnie.extratrees.craftgui.kitchen;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel {
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(IWidget parent, float x, float y, float width, float height) {
		super(parent, x, y, width, 2.0f, MinecraftGUI.PanelType.Gray);
		stayOpenOnChildClick = false;
		addAttribute(WidgetAttribute.CanFocus);
	}
}
