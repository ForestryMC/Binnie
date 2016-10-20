// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.controls.core.Control;

public class ControlTooltip extends Control implements ITooltip
{
	public ControlTooltip(final IWidget parent, final float x, final float y, final float w, final float h) {
		super(parent, x, y, w, h);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
	}
}
