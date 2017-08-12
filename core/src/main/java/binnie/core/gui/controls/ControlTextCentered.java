package binnie.core.gui.controls;

import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;

public class ControlTextCentered extends ControlText {
	public ControlTextCentered(final IWidget parent, final int y, final String text) {
		super(parent, new Area(new Point(0, y), new Point(parent.getSize().xPos(), 0)), text, TextJustification.TOP_CENTER);
	}
}
