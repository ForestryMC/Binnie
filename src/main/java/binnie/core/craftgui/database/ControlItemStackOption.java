package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import net.minecraft.item.ItemStack;

public class ControlItemStackOption extends ControlTextOption<ItemStack> {
    private ControlItemDisplay controlBee;

    public ControlItemStackOption(ControlList<ItemStack> controlList, ItemStack option, int y) {
        super(controlList, option, option.getDisplayName(), y);
        setSize(new IPoint(getSize().x(), 20.0f));

        controlBee = new ControlItemDisplay(this, 2.0f, 2.0f);
        controlBee.setItemStack(option);

        addAttribute(WidgetAttribute.MOUSE_OVER);
        CraftGUIUtil.moveWidget(textWidget, new IPoint(22.0f, 0.0f));
        textWidget.setSize(textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
        int th = (int) CraftGUI.render.textHeight(
                textWidget.getValue(), textWidget.getSize().x());
        int height = Math.max(20, th + 6);
        setSize(new IPoint(size().x(), height));
        textWidget.setSize(new IPoint(textWidget.getSize().x(), height));
        controlBee.setPosition(new IPoint(controlBee.pos().x(), (height - 18) / 2));
    }
}
