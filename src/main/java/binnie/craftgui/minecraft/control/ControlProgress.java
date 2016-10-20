package binnie.craftgui.minecraft.control;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.resource.Texture;

public class ControlProgress extends ControlProgressBase {
    private Texture progressBlank;
    private Texture progressBar;
    private Position direction;

    public ControlProgress(final IWidget parent, final int x, final int y, final Texture progressBlank, final Texture progressBar, final Position dir) {
        super(parent, x, y, (progressBlank == null) ? 0.0f : progressBlank.w(), (progressBlank == null) ? 0.0f : progressBlank.h());
        this.progressBlank = progressBlank;
        this.progressBar = progressBar;
        this.progress = 0.0f;
        this.direction = dir;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(this.progressBlank, this.getArea());
        CraftGUI.Render.texturePercentage(this.progressBar, this.getArea(), this.direction, this.progress);
    }
}
