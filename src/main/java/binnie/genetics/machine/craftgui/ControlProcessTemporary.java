package binnie.genetics.machine.craftgui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlMachineProgress;
import binnie.core.gui.renderer.RenderUtil;

public class ControlProcessTemporary extends ControlMachineProgress {
	public ControlProcessTemporary(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, null, null, null);
		this.setSize(new Point(width, height));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(this.getArea(), -4868683);
		final int width = Math.round(this.getSize().y() * this.progress / 100.0f);
		Area area = new Area(getArea());
		area.setWidth(width);
		RenderUtil.drawSolidRect(area, -65536);
	}
}
