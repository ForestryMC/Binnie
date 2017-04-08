package binnie.extratrees.kitchen.craftgui;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel {
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, 2, MinecraftGUI.PanelType.Gray);
		this.stayOpenOnChildClick = false;
		this.addAttribute(Attribute.CanFocus);
	}
}
