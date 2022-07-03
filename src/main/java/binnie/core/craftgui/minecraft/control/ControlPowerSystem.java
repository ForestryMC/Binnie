package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.power.PowerSystem;
import binnie.core.util.I18N;

public class ControlPowerSystem extends Control implements ITooltip {
    private PowerSystem system;

    public ControlPowerSystem(IWidget parent, float x, float y, PowerSystem system) {
        super(parent, x, y, 16.0f, 16.0f);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        this.system = system;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.PowerButton, getArea());
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.setType(Tooltip.Type.POWER);
        tooltip.add(I18N.localise("binniecore.gui.tooltip.powerSupply"));
        tooltip.add(I18N.localise("binniecore.gui.tooltip.poweredBy", system.getUnitName()));
        tooltip.setMaxWidth(200);
    }
}
