package binnie.core.craftgui.controls;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;

public class ControlTextCentered extends ControlText {
	public ControlTextCentered(final IWidget parent, final int y, final String text) {
		super(parent, new Area(new Point(0, y), new Point(parent.size().x(), 0)), text, TextJustification.TOP_CENTER);
	}
}
