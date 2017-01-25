package binnie.extratrees.kitchen.craftgui;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel {
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, 2, MinecraftGUI.PanelType.Gray);
		this.stayOpenOnChildClick = false;
		this.addAttribute(Attribute.CanFocus);
	}
}
