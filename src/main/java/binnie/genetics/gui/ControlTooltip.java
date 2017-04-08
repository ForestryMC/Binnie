package binnie.genetics.gui;

import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlTooltip extends Control implements ITooltip {
	public ControlTooltip(final IWidget parent, final int x, final int y, final int w, final int h) {
		super(parent, x, y, w, h);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
	}
}
