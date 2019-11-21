package binnie.botany.gui.database;

import binnie.botany.api.genetics.IFlowerColor;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlTextOption;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ControlColorOption extends ControlTextOption<IFlowerColor> {
	private final ControlColorDisplay controlBee;
	private final Point boxPosition;

	public ControlColorOption(ControlList<IFlowerColor> controlList, IFlowerColor option, int y) {
		super(controlList, option, option.getColorName(), y);
		setSize(new Point(getSize().xPos(), 20));
		controlBee = new ControlColorDisplay(this, 2, 2, option);
		addAttribute(Attribute.MOUSE_OVER);
		CraftGUIUtil.moveWidget(textWidget, new Point(22, 0));
		textWidget.setSize(textWidget.getSize().sub(new Point(24, 0)));
		int th = CraftGUI.RENDER.textHeight(textWidget.getValue(), textWidget.getSize().xPos());
		int height = Math.max(20, th + 6);
		setSize(new Point(this.getSize().xPos(), height));
		textWidget.setSize(new Point(textWidget.getSize().xPos(), height));
		boxPosition = new Point(2, (height - 18) / 2);
	}
}
