package binnie.genetics.machine.splicer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.control.ControlProgressBase;
import binnie.core.gui.renderer.RenderUtil;

public class ControlSplicerProgress extends ControlProgressBase {
	public ControlSplicerProgress(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		float progress = this.getProcess().getCurrentProgress() / 100.0f;
		float n = this.getProcess().getProcessTime() / 12.0f;
		int spacing = 10;
		float range = this.getWidth();
		int h = 8;
		float ooy = -((n - 1.0f) * spacing) - range / 2.0f;
		float ddy = (n - 1.0f) * spacing + range;
		int oy = Math.round(ooy + ddy * progress);
		for (int i = 0; i < n; ++i) {
			int seed = 432523;
			int[] colours = {10027008, 30464, 255, 10057472};
			int c1 = colours[(int) Math.abs(13.0 * Math.sin(i) + 48.0 * Math.cos(i) + 25.0 * Math.sin(7 * i)) % 4];
			int c2 = colours[(int) Math.abs(23.0 * Math.sin(i) + 28.0 * Math.cos(i) + 15.0 * Math.sin(7 * i)) % 4];
			int c3 = colours[(int) Math.abs(43.0 * Math.sin(i) + 38.0 * Math.cos(i) + 55.0 * Math.sin(7 * i)) % 4];
			int c4 = colours[(int) Math.abs(3.0 * Math.sin(i) + 18.0 * Math.cos(i) + 35.0 * Math.sin(7 * i)) % 4];
			int y = oy + i * spacing;
			if (y > -range / 2.0f && y < range / 2.0f) {
				float percentView = (float) Math.sqrt(1.0f - Math.abs(2.0f * y / range));
				float offMovement = (this.getHeight() - 2.0f * h) / 2.0f;
				int alpha = 16777216 * (int) (255.0f * percentView);
				c1 += alpha;
				c2 += alpha;
				c3 += alpha;
				c4 += alpha;
				int viewAmount = Math.round(offMovement * percentView);
				RenderUtil.drawSolidRectWithAlpha(new Area(this.getWidth() / 2 + y, viewAmount, 4, h / 2), c1);
				RenderUtil.drawSolidRectWithAlpha(new Area(this.getWidth() / 2 + y, viewAmount + 4, 4, h / 2), (y < 0) ? c2 : c3);
				RenderUtil.drawSolidRectWithAlpha(new Area(this.getWidth() / 2 + y, this.getHeight() - viewAmount - 8, 4, h / 2), (y < 0) ? c3 : c2);
				RenderUtil.drawSolidRectWithAlpha(new Area(this.getWidth() / 2 + y, this.getHeight() - viewAmount - 4, 4, h / 2), c4);
			}
		}
	}
}
