package binnie.botany.craftgui;

import binnie.botany.api.IFlowerColor;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IPoint;

public class ControlColorOption extends ControlTextOption<IFlowerColor> {
    protected ControlColorDisplay controlBee;
    protected IPoint boxPosition;

    public ControlColorOption(ControlList<IFlowerColor> controlList, IFlowerColor option, int y) {
        super(controlList, option, option.getName(), y);
        setSize(new IPoint(getSize().x(), 20.0f));
        (controlBee = new ControlColorDisplay(this, 2.0f, 2.0f)).setValue(option);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        CraftGUIUtil.moveWidget(textWidget, new IPoint(22.0f, 0.0f));
        textWidget.setSize(textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
        int th = (int) CraftGUI.render.textHeight(
                textWidget.getValue(), textWidget.getSize().x());
        int height = Math.max(20, th + 6);
        setSize(new IPoint(size().x(), height));
        textWidget.setSize(new IPoint(textWidget.getSize().x(), height));
        boxPosition = new IPoint(2.0f, (height - 18) / 2);
    }
}
