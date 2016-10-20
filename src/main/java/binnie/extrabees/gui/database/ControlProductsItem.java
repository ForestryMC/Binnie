package binnie.extrabees.gui.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlItemDisplay;

import java.text.DecimalFormat;

public class ControlProductsItem extends ControlOption<ControlProductsBox.Product> {
    ControlItemDisplay item;

    public ControlProductsItem(final ControlList<ControlProductsBox.Product> controlList, final ControlProductsBox.Product value, final int y) {
        super(controlList, value, y);
        (this.item = new ControlItemDisplay(this, 4.0f, 4.0f)).setTooltip();
        final ControlText textWidget = new ControlTextCentered(this, 2.0f, "");
        CraftGUIUtil.moveWidget(textWidget, new IPoint(12.0f, 0.0f));
        if (value != null) {
            this.item.setItemStack(value.item);
            final float time = (int) (55000.0 / value.chance);
            final float seconds = time / 20.0f;
            final float minutes = seconds / 60.0f;
            final float hours = minutes / 60.0f;
            final DecimalFormat df = new DecimalFormat("#.0");
            if (hours > 1.0f) {
                textWidget.setValue("Every " + df.format(hours) + " hours");
            } else if (minutes > 1.0f) {
                textWidget.setValue("Every " + df.format(minutes) + " min.");
            } else {
                textWidget.setValue("Every " + df.format(seconds) + " sec.");
            }
        }
    }
}
