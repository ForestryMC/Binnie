package binnie.craftgui.botany;

import binnie.botany.api.IFlowerColour;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IPoint;

public class ControlColourOption extends ControlTextOption<IFlowerColour> {
    ControlColourDisplay controlBee;
    IPoint boxPosition;

    public ControlColourOption(final ControlList<IFlowerColour> controlList, final IFlowerColour option, final int y) {
        super(controlList, option, option.getName(), y);
        this.setSize(new IPoint(this.getSize().x(), 20.0f));
        (this.controlBee = new ControlColourDisplay(this, 2.0f, 2.0f)).setValue(option);
        this.addAttribute(Attribute.MouseOver);
        CraftGUIUtil.moveWidget(this.textWidget, new IPoint(22.0f, 0.0f));
        this.textWidget.setSize(this.textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
        final int th = (int) CraftGUI.Render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
        final int height = Math.max(20, th + 6);
        this.setSize(new IPoint(this.size().x(), height));
        this.textWidget.setSize(new IPoint(this.textWidget.getSize().x(), height));
        this.boxPosition = new IPoint(2.0f, (height - 18) / 2);
    }
}
