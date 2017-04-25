// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.geometry.IPoint;
import binnie.botany.api.IFlowerColour;
import binnie.core.craftgui.controls.listbox.ControlTextOption;

public class ControlColourOption extends ControlTextOption<IFlowerColour>
{
	ControlColourDisplay controlBee;
	IPoint boxPosition;

	public ControlColourOption(final ControlList<IFlowerColour> controlList, final IFlowerColour option, final int y) {
		super(controlList, option, option.getName(), y);
		this.setSize(new IPoint(this.getSize().x(), 20.0f));
		(this.controlBee = new ControlColourDisplay(this, 2.0f, 2.0f)).setValue(option);
		this.addAttribute(WidgetAttribute.MouseOver);
		CraftGUIUtil.moveWidget(this.textWidget, new IPoint(22.0f, 0.0f));
		this.textWidget.setSize(this.textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
		final int th = (int) CraftGUI.Render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		this.setSize(new IPoint(this.size().x(), height));
		this.textWidget.setSize(new IPoint(this.textWidget.getSize().x(), height));
		this.boxPosition = new IPoint(2.0f, (height - 18) / 2);
	}
}
