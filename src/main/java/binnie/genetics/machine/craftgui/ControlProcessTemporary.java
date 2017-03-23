package binnie.genetics.machine.craftgui;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.minecraft.control.ControlMachineProgress;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
