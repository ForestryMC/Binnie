package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.resource.Texture;

public class ControlProgress extends ControlProgressBase {
    private Texture progressBlank;
    private Texture progressBar;
    private Position direction;

    public ControlProgress(IWidget parent, int x, int y, Texture progressBlank, Texture progressBar, Position dir) {
        super(
                parent,
                x,
                y,
                (progressBlank == null) ? 0.0f : progressBlank.w(),
                (progressBlank == null) ? 0.0f : progressBlank.h());
        this.progressBlank = progressBlank;
        this.progressBar = progressBar;
        progress = 0.0f;
        direction = dir;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(progressBlank, getArea());
        CraftGUI.render.texturePercentage(progressBar, getArea(), direction, progress);
    }
}
