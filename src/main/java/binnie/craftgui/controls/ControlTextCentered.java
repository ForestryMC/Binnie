package binnie.craftgui.controls;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.geometry.TextJustification;

public class ControlTextCentered extends ControlText {
	public ControlTextCentered(final IWidget parent, final int y, final String text) {
		super(parent, new Area(new Point(0, y), new Point(parent.size().x(), 0)), text, TextJustification.TopCenter);
	}
}
