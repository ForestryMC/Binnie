// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import net.minecraft.item.ItemStack;

public class ControlItemStackOption extends ControlTextOption<ItemStack>
{
	private ControlItemDisplay controlBee;

	public ControlItemStackOption(final ControlList<ItemStack> controlList, final ItemStack option, final int y) {
		super(controlList, option, option.getDisplayName(), y);
		setSize(new IPoint(getSize().x(), 20.0f));
		(controlBee = new ControlItemDisplay(this, 2.0f, 2.0f)).setItemStack(option);
		addAttribute(Attribute.MouseOver);
		CraftGUIUtil.moveWidget(textWidget, new IPoint(22.0f, 0.0f));
		textWidget.setSize(textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
		final int th = (int) CraftGUI.Render.textHeight(textWidget.getValue(), textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		setSize(new IPoint(size().x(), height));
		textWidget.setSize(new IPoint(textWidget.getSize().x(), height));
		controlBee.setPosition(new IPoint(controlBee.pos().x(), (height - 18) / 2));
	}
}
