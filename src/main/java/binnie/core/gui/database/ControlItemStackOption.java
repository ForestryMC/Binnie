package binnie.core.gui.database;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlTextOption;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlItemDisplay;

@SideOnly(Side.CLIENT)
public class ControlItemStackOption extends ControlTextOption<ItemStack> {
	private ControlItemDisplay controlBee;

	public ControlItemStackOption(final ControlList<ItemStack> controlList, final ItemStack option, final int y) {
		super(controlList, option, option.getDisplayName(), y);
		this.setSize(new Point(this.getSize().x(), 20));
		(this.controlBee = new ControlItemDisplay(this, 2, 2)).setItemStack(option);
		this.addAttribute(Attribute.MouseOver);
		CraftGUIUtil.moveWidget(this.textWidget, new Point(22, 0));
		this.textWidget.setSize(this.textWidget.getSize().sub(new Point(24, 0)));
		final int th = CraftGUI.RENDER.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		this.setSize(new Point(this.size().x(), height));
		this.textWidget.setSize(new Point(this.textWidget.getSize().x(), height));
		this.controlBee.setPosition(new Point(this.getPosition().x(), (height - 18) / 2));
	}
}
