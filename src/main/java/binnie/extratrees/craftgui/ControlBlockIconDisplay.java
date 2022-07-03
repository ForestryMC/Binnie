package binnie.extratrees.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import net.minecraft.util.IIcon;

public class ControlBlockIconDisplay extends Control {
    protected IIcon icon;

    public ControlBlockIconDisplay(IWidget parent, float x, float y, IIcon icon) {
        super(parent, x, y, 18.0f, 18.0f);
        this.icon = icon;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.iconBlock(IPoint.ZERO, icon);
    }
}
