// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import net.minecraft.util.IIcon;

public class ControlIconDisplay extends Control
{
	private IIcon icon;

	public ControlIconDisplay(final IWidget parent, final float x, final float y, final IIcon icon) {
		super(parent, x, y, 16.0f, 16.0f);
		this.icon = null;
		this.icon = icon;
	}

	@Override
	public void onRenderForeground() {
		CraftGUI.Render.iconItem(IPoint.ZERO, icon);
	}
}
