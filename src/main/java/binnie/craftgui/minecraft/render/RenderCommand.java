// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft.render;

import binnie.craftgui.minecraft.GuiCraftGUI;
import binnie.craftgui.core.IWidget;

public abstract class RenderCommand
{
	IWidget widget;

	public RenderCommand(final IWidget widget) {
		this.widget = widget;
	}

	public abstract void render(final GuiCraftGUI p0);
}
