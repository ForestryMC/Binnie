package binnie.core.gui.resource.minecraft;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.resource.Texture;
import binnie.core.resource.IBinnieTexture;

public class PaddedTexture extends Texture {
	public PaddedTexture(final int u, final int v, final int w, final int h, final int offset, final IBinnieTexture textureFile, final int leftPadding, final int rightPadding, final int topPadding, final int bottomPadding) {
		super(new Area(u, v, w, h), new Border(topPadding, rightPadding, bottomPadding, leftPadding), new Border(offset), textureFile);
	}
}
