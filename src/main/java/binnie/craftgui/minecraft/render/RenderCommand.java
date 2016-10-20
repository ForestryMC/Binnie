package binnie.craftgui.minecraft.render;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.GuiCraftGUI;

public abstract class RenderCommand {
    IWidget widget;

    public RenderCommand(final IWidget widget) {
        this.widget = widget;
    }

    public abstract void render(final GuiCraftGUI p0);
}
