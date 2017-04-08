package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.GuiCraftGUI;

public abstract class RenderCommand {
	IWidget widget;

	public RenderCommand(final IWidget widget) {
		this.widget = widget;
	}

	public abstract void render(final GuiCraftGUI p0);
}
