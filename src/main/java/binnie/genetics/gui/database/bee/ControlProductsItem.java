package binnie.genetics.gui.database.bee;

import java.text.DecimalFormat;

import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlOption;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;

public class ControlProductsItem extends ControlOption<ControlProductsBox.Product> {
	private ControlItemDisplay item;

	public ControlProductsItem(ControlList<ControlProductsBox.Product> controlList, ControlProductsBox.Product value, int y) {
		super(controlList, value, y);
		(item = new ControlItemDisplay(this, 4, 4)).setTooltip();
		ControlText textWidget = new ControlTextCentered(this, 2, "");
		CraftGUIUtil.moveWidget(textWidget, new Point(12, 0));
		item.setItemStack(value.item);
		int time = (int) (55000.0 / value.chance);
		int seconds = time / 20;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		DecimalFormat df = new DecimalFormat("#.0");

		if (hours > 1) {
			textWidget.setValue(I18N.localise(DatabaseConstants.BEE_CONTROL_KEY + ".time.hours", df.format(hours)));
		} else if (minutes > 1) {
			textWidget.setValue(I18N.localise(DatabaseConstants.BEE_CONTROL_KEY + ".time.min", df.format(minutes)));
		} else {
			textWidget.setValue(I18N.localise(DatabaseConstants.BEE_CONTROL_KEY + ".time.sec", df.format(seconds)));
		}
	}
}
