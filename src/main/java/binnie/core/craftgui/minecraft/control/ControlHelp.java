package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.util.I18N;

public class ControlHelp extends Control implements ITooltip {
    public ControlHelp(IWidget parent, float x, float y) {
        super(parent, x, y, 16.0f, 16.0f);
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.HelpButton, getArea());
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.setType(Tooltip.Type.HELP);
        tooltip.add(I18N.localise("binniecore.gui.tooltip.help"));
        tooltip.add(I18N.localise("binniecore.gui.tooltip.help.0"));
        tooltip.add(I18N.localise("binniecore.gui.tooltip.help.1"));
        tooltip.add(I18N.localise("binniecore.gui.tooltip.help.2"));
    }

    @Override
    public void getHelpTooltip(Tooltip tooltip) {
        getTooltip(tooltip);
    }
}
