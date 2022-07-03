package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;

public class ControlImage extends Control {
    private Object key;

    public ControlImage(IWidget parent, float x, float y, Texture text) {
        super(parent, x, y, text.w(), text.h());
        key = text;
    }

    @Override
    public void onRenderForeground() {
        CraftGUI.render.texture(key, IPoint.ZERO);
    }
}
