package binnie.craftgui.mod.database;

import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		final int th = CraftGUI.render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		this.setSize(new Point(this.size().x(), height));
		this.textWidget.setSize(new Point(this.textWidget.getSize().x(), height));
		this.controlBee.setPosition(new Point(this.controlBee.pos().x(), (height - 18) / 2));
	}
}
