// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.kitchen;

import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel
{
	public boolean stayOpenOnChildClick;

	public ControlDropDownMenu(final IWidget parent, final float x, final float y, final float width, final float height) {
		super(parent, x, y, width, 2.0f, MinecraftGUI.PanelType.Gray);
		this.stayOpenOnChildClick = false;
		this.addAttribute(WidgetAttribute.CanFocus);
	}
}
