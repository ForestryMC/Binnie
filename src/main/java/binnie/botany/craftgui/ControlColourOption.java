package binnie.botany.craftgui;

import binnie.botany.api.IFlowerColour;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.Point;

public class ControlColourOption extends ControlTextOption<IFlowerColour> {
	ControlColourDisplay controlBee;
	Point boxPosition;

	public ControlColourOption(final ControlList<IFlowerColour> controlList, final IFlowerColour option, final int y) {
		super(controlList, option, option.getColourName(), y);
		this.setSize(new Point(this.getSize().x(), 20));
		this.controlBee = new ControlColourDisplay(this, 2, 2, option);
		this.addAttribute(Attribute.MouseOver);
		CraftGUIUtil.moveWidget(this.textWidget, new Point(22, 0));
		this.textWidget.setSize(this.textWidget.getSize().sub(new Point(24, 0)));
		final int th = CraftGUI.render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		this.setSize(new Point(this.size().x(), height));
		this.textWidget.setSize(new Point(this.textWidget.getSize().x(), height));
		this.boxPosition = new Point(2, (height - 18) / 2);
	}
}
