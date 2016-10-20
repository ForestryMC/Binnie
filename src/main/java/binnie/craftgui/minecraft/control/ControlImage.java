package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.resource.Texture;

public class ControlImage extends Control {
    private Object key;

    public ControlImage(final IWidget parent, final float x, final float y, final Texture text) {
        super(parent, x, y, text.w(), text.h());
        this.key = null;
        this.key = text;
    }

    @Override
    public void onRenderForeground() {
        CraftGUI.Render.texture(this.key, IPoint.ZERO);
    }
}
