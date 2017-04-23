// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.control.ControlMachineProgress;

public class ControlProcessTemporary extends ControlMachineProgress
{
	public ControlProcessTemporary(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, null, null, null);
		this.setSize(new IPoint(width, height));
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.solid(this.getArea(), -4868683);
		final float w = this.getSize().y() * this.progress / 100.0f;
		CraftGUI.Render.solid(new IArea(this.getArea().x(), this.getArea().y(), w, this.getArea().h()), -65536);
	}
}
