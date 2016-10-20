package binnie.craftgui.genetics.machine;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.control.ControlProgressBase;

public class ControlSplicerProgress extends ControlProgressBase {
    float strength;

    public ControlSplicerProgress(final IWidget parent, final float x, final float y, final float w, final float h) {
        super(parent, x, y, w, h);
        this.strength = 0.0f;
        this.strength = 0.0f;
    }

    @Override
    public void onRenderBackground() {
        final float progress = this.getProcess().getCurrentProgress() / 100.0f;
        final float n = this.getProcess().getProcessTime() / 12.0f;
        final float spacing = 10.0f;
        final float range = this.w();
        final float h = 8.0f;
        final float ooy = -((n - 1.0f) * spacing) - range / 2.0f;
        final float ddy = (n - 1.0f) * spacing + range;
        final float oy = ooy + ddy * progress;
        for (int i = 0; i < n; ++i) {
            final int seed = 432523;
            final int[] colours = {10027008, 30464, 255, 10057472};
            int c1 = colours[(int) Math.abs(13.0 * Math.sin(i) + 48.0 * Math.cos(i) + 25.0 * Math.sin(7 * i)) % 4];
            int c2 = colours[(int) Math.abs(23.0 * Math.sin(i) + 28.0 * Math.cos(i) + 15.0 * Math.sin(7 * i)) % 4];
            int c3 = colours[(int) Math.abs(43.0 * Math.sin(i) + 38.0 * Math.cos(i) + 55.0 * Math.sin(7 * i)) % 4];
            int c4 = colours[(int) Math.abs(3.0 * Math.sin(i) + 18.0 * Math.cos(i) + 35.0 * Math.sin(7 * i)) % 4];
            final float y = oy + i * spacing;
            if (y > -range / 2.0f && y < range / 2.0f) {
                final float percentView = (float) Math.sqrt(1.0f - Math.abs(2.0f * y / range));
                final float offMovement = (this.h() - 2.0f * h) / 2.0f;
                final int alpha = 16777216 * (int) (255.0f * percentView);
                c1 += alpha;
                c2 += alpha;
                c3 += alpha;
                c4 += alpha;
                CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0f + y, offMovement * percentView, 4.0f, h / 2.0f), c1);
                CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0f + y, offMovement * percentView + 4.0f, 4.0f, h / 2.0f), (y < 0.0f) ? c2 : c3);
                CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0f + y, this.h() - offMovement * percentView - 8.0f, 4.0f, h / 2.0f), (y < 0.0f) ? c3 : c2);
                CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0f + y, this.h() - offMovement * percentView - 4.0f, 4.0f, h / 2.0f), c4);
            }
        }
    }
}
