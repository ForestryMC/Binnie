package binnie.craftgui.minecraft.control;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.resource.Texture;

public class ControlMachineProgress extends ControlProgress {
    public ControlMachineProgress(final IWidget parent, final int x, final int y, final Texture base, final Texture progress, final Position dir) {
        super(parent, x, y, base, progress, dir);
    }
}
