package binnie.craftgui.controls;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;

public class ControlTextCentered extends ControlText {
    public ControlTextCentered(final IWidget parent, final float y, final String text) {
        super(parent, new IArea(new IPoint(0.0f, y), new IPoint(parent.size().x(), 0.0f)), text, TextJustification.TopCenter);
    }
}
