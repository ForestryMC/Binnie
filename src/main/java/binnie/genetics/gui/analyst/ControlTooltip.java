package binnie.genetics.gui.analyst;

import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlTooltip extends Control implements ITooltip {
	public ControlTooltip(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
	}
}
