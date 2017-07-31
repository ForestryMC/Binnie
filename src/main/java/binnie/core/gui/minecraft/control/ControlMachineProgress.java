package binnie.core.gui.minecraft.control;

import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.resource.Texture;

public class ControlMachineProgress extends ControlProgress {
	public ControlMachineProgress(final IWidget parent, final int x, final int y, final Texture base, final Texture progress, final Position dir) {
		super(parent, x, y, base, progress, dir);
	}
}
