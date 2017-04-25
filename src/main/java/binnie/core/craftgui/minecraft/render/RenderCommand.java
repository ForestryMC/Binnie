// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.GuiCraftGUI;

public abstract class RenderCommand
{
	IWidget widget;

	public RenderCommand(IWidget widget) {
		this.widget = widget;
	}

	public abstract void render(GuiCraftGUI p0);
}
