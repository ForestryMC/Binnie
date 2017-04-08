package binnie.extrabees.gui.database;

import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;

import java.text.DecimalFormat;

public class ControlProductsItem extends ControlOption<ControlProductsBox.Product> {
	ControlItemDisplay item;

	public ControlProductsItem(final ControlList<ControlProductsBox.Product> controlList, final ControlProductsBox.Product value, final int y) {
		super(controlList, value, y);
		(this.item = new ControlItemDisplay(this, 4, 4)).setTooltip();
		final ControlText textWidget = new ControlTextCentered(this, 2, "");
		CraftGUIUtil.moveWidget(textWidget, new Point(12, 0));
		if (value != null) {
			this.item.setItemStack(value.item);
			final int time = (int) (55000.0 / value.chance);
			final int seconds = time / 20;
			final int minutes = seconds / 60;
			final int hours = minutes / 60;
			final DecimalFormat df = new DecimalFormat("#.0");
			if (hours > 1) {
				textWidget.setValue("Every " + df.format(hours) + " hours");
			} else if (minutes > 1) {
				textWidget.setValue("Every " + df.format(minutes) + " min.");
			} else {
				textWidget.setValue("Every " + df.format(seconds) + " sec.");
			}
		}
	}
}
