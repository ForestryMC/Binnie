package binnie.core.gui.minecraft.control;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.resource.textures.Texture;

public class ControlMachineProgress extends ControlProgress {
	public ControlMachineProgress(IWidget parent, int x, int y, Texture base, Texture progress, Alignment dir) {
		super(parent, x, y, base, progress, dir);
	}
}
