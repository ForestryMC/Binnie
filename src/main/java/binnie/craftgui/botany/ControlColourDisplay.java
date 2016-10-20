package binnie.craftgui.botany;

import binnie.botany.api.IFlowerColour;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.*;

public class ControlColourDisplay extends Control implements IControlValue<IFlowerColour>, ITooltip {
    IFlowerColour value;

    public ControlColourDisplay(final IWidget parent, final float x, final float y) {
        super(parent, x, y, 16.0f, 16.0f);
        this.addAttribute(Attribute.MouseOver);
    }

    @Override
    public IFlowerColour getValue() {
        return this.value;
    }

    @Override
    public void setValue(final IFlowerColour value) {
        this.value = value;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.solid(this.getArea(), -1);
        CraftGUI.Render.solid(this.getArea().inset(1), -16777216 + this.getValue().getColor(false));
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        super.getTooltip(tooltip);
        tooltip.add(this.value.getName());
    }
}
