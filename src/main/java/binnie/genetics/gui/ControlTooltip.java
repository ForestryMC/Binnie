// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlTooltip extends Control implements ITooltip
{
	public ControlTooltip(final IWidget parent, final float x, final float y, final float w, final float h) {
		super(parent, x, y, w, h);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
	}
}
