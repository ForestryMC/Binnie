package binnie.genetics.machine.craftgui;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.minecraft.control.ControlMachineProgress;

public class ControlProcessTemporary extends ControlMachineProgress {
	public ControlProcessTemporary(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, null, null, null);
		this.setSize(new IPoint(width, height));
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(this.getArea(), -4868683);
		final int w = Math.round(this.getSize().y() * this.progress / 100.0f);
		RenderUtil.drawSolidRect(new IArea(this.getArea().x(), this.getArea().y(), w, this.getArea().h()), -65536);
	}
}
