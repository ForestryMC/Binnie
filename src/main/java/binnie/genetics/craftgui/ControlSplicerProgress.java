package binnie.genetics.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;

public class ControlSplicerProgress extends ControlProgressBase {
    float strength;

    public ControlSplicerProgress(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        strength = 0.0f;
        strength = 0.0f;
    }

    @Override
    public void onRenderBackground() {
        float progress = getProcess().getCurrentProgress() / 100.0f;
        float n = getProcess().getProcessTime() / 12.0f;
        float spacing = 10.0f;
        float range = w();
        float h = 8.0f;
        float ooy = -((n - 1.0f) * spacing) - range / 2.0f;
        float ddy = (n - 1.0f) * spacing + range;
        float oy = ooy + ddy * progress;

        for (int i = 0; i < n; ++i) {
            int seed = 432523;
            int[] colours = {0x990000, 0x007700, 0x0000ff, 0x997700};
            int c1 = colours[(int) Math.abs(13.0 * Math.sin(i) + 48.0 * Math.cos(i) + 25.0 * Math.sin(7 * i)) % 4];
            int c2 = colours[(int) Math.abs(23.0 * Math.sin(i) + 28.0 * Math.cos(i) + 15.0 * Math.sin(7 * i)) % 4];
            int c3 = colours[(int) Math.abs(43.0 * Math.sin(i) + 38.0 * Math.cos(i) + 55.0 * Math.sin(7 * i)) % 4];
            int c4 = colours[(int) Math.abs(3.0 * Math.sin(i) + 18.0 * Math.cos(i) + 35.0 * Math.sin(7 * i)) % 4];
            float y = oy + i * spacing;

            if (y > -range / 2.0f && y < range / 2.0f) {
                float percentView = (float) Math.sqrt(1.0f - Math.abs(2.0f * y / range));
                float offMovement = (h() - 2.0f * h) / 2.0f;
                int alpha = 16777216 * (int) (255.0f * percentView);
                c1 += alpha;
                c2 += alpha;
                c3 += alpha;
                c4 += alpha;
                CraftGUI.render.solidAlpha(new IArea(w() / 2.0f + y, offMovement * percentView, 4.0f, h / 2.0f), c1);
                CraftGUI.render.solidAlpha(
                        new IArea(w() / 2.0f + y, offMovement * percentView + 4.0f, 4.0f, h / 2.0f),
                        (y < 0.0f) ? c2 : c3);
                CraftGUI.render.solidAlpha(
                        new IArea(w() / 2.0f + y, h() - offMovement * percentView - 8.0f, 4.0f, h / 2.0f),
                        (y < 0.0f) ? c3 : c2);
                CraftGUI.render.solidAlpha(
                        new IArea(w() / 2.0f + y, h() - offMovement * percentView - 4.0f, 4.0f, h / 2.0f), c4);
            }
        }
    }
}
