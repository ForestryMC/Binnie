package binnie.genetics.gui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;

public class ControlTooltip extends Control implements ITooltip {
    public ControlTooltip(final IWidget parent, final float x, final float y, final float w, final float h) {
        super(parent, x, y, w, h);
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
    }
}
