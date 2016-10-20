package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlInfo extends Control implements ITooltip {
    private String info;

    public ControlInfo(final IWidget parent, final float x, final float y, final String info) {
        super(parent, x, y, 16.0f, 16.0f);
        this.addAttribute(Attribute.MouseOver);
        this.info = info;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.InfoButton, this.getArea());
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        tooltip.setType(Tooltip.Type.Information);
        tooltip.add("Info");
        tooltip.add(this.info);
        tooltip.setMaxWidth(200);
    }
}
