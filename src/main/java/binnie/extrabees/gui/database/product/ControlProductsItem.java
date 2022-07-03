package binnie.extrabees.gui.database.product;

import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import java.text.DecimalFormat;

public class ControlProductsItem extends ControlOption<Product> {
    protected ControlItemDisplay item;

    public ControlProductsItem(ControlList<Product> controlList, Product value, int y) {
        super(controlList, value, y);
        (item = new ControlItemDisplay(this, 4.0f, 4.0f)).setTooltip();
        ControlText textWidget = new ControlTextCentered(this, 2.0f, "");
        CraftGUIUtil.moveWidget(textWidget, new IPoint(12.0f, 0.0f));

        if (value == null) {
            return;
        }

        item.setItemStack(value.item);
        float time = (int) (550.0 / value.chance);
        float seconds = time / 20.0f;
        float minutes = seconds / 60.0f;
        float hours = minutes / 60.0f;
        DecimalFormat df = new DecimalFormat("#.0");

        if (hours > 1.0f) {
            textWidget.setValue(
                    I18N.localise("extrabees.gui.database.tab.species.products.everyHours", df.format(hours)));
        } else if (minutes > 1.0f) {
            textWidget.setValue(
                    I18N.localise("extrabees.gui.database.tab.species.products.everyMins", df.format(minutes)));
        } else {
            textWidget.setValue(
                    I18N.localise("extrabees.gui.database.tab.species.products.everySecs", df.format(seconds)));
        }
    }
}
