package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.resource.Texture;

public class ControlMachineProgress extends ControlProgress {
    public ControlMachineProgress(IWidget parent, int x, int y, Texture base, Texture progress, Position dir) {
        super(parent, x, y, base, progress, dir);
    }
}
