package binnie.core.machines.storage;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.minecraft.EnumColor;

class ControlColourSelector extends Control implements ITooltip, IControlValue<EnumColor> {
    private EnumColor value;

    public ControlColourSelector(IWidget parent, float x, float y, float w, float h, EnumColor value) {
        super(parent, x, y, w, h);
        setValue(value);
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.add(value.toString());
    }

    @Override
    public EnumColor getValue() {
        return value;
    }

    @Override
    public void setValue(EnumColor value) {
        this.value = value;
        setColor(value.getColor());
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.render.gradientRect(getArea(), 0xff000000 + value.getColor(), 0xff000000 + value.getColor());
    }
}
