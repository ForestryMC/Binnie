package binnie.extratrees.kitchen.craftgui;

import binnie.core.gui.Attribute;
import binnie.core.gui.IWidget;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.window.Panel;

public class ControlDropDownMenu extends Panel {
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, 2, MinecraftGUI.PanelType.Gray);
		this.stayOpenOnChildClick = false;
		this.addAttribute(Attribute.CAN_FOCUS);
	}
}
