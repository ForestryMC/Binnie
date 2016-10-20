package binnie.core.machines.storage;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.*;
import binnie.craftgui.minecraft.EnumColor;

class ControlColourSelector extends Control implements ITooltip, IControlValue<EnumColor> {
    private EnumColor value;

    public ControlColourSelector(final IWidget parent, final float x, final float y, final float w, final float h, final EnumColor value) {
        super(parent, x, y, w, h);
        this.setValue(value);
        this.addAttribute(Attribute.MouseOver);
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        tooltip.add(this.value.toString());
    }

    @Override
    public EnumColor getValue() {
        return this.value;
    }

    @Override
    public void setValue(final EnumColor value) {
        this.value = value;
        this.setColour(this.getValue().getColour());
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.Render.gradientRect(this.getArea(), -16777216 + this.value.getColour(), -16777216 + this.value.getColour());
    }
}
