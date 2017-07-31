package binnie.core.gui.minecraft.render;

import binnie.core.gui.IWidget;
import binnie.core.gui.minecraft.GuiCraftGUI;

public abstract class RenderCommand {
	IWidget widget;

	public RenderCommand(final IWidget widget) {
		this.widget = widget;
	}

	public abstract void render(final GuiCraftGUI p0);
}
