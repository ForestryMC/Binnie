package binnie.genetics.gui.analyst;

import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;

public class ControlTooltip extends Control implements ITooltip {
	public ControlTooltip(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
	}
}
