package binnie.extratrees.kitchen.craftgui;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.window.Panel;

public class ControlDropDownMenu extends Panel {
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width, 2, MinecraftGUI.PanelType.GRAY);
		this.stayOpenOnChildClick = false;
		this.addAttribute(Attribute.CAN_FOCUS);
	}
}
