package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.GuiCraftGUI;

// TODO unused class?
public abstract class RenderCommand {
	protected IWidget widget;

	public RenderCommand(IWidget widget) {
		this.widget = widget;
	}

	public abstract void render(GuiCraftGUI p0);
}
