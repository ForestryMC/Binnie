// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;

public class ControlTextCentered extends ControlText
{
	public ControlTextCentered(final IWidget parent, final float y, final String text) {
		super(parent, new IArea(new IPoint(0.0f, y), new IPoint(parent.size().x(), 0.0f)), text, TextJustification.TopCenter);
	}
}
