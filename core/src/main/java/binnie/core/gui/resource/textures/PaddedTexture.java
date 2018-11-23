package binnie.core.gui.resource.textures;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.resource.IBinnieTexture;

public class PaddedTexture extends Texture {
	public PaddedTexture(int u, int v, int w, int h, int offset, IBinnieTexture textureFile, int leftPadding, int rightPadding, int topPadding, int bottomPadding) {
		super(new Area(u, v, w, h), new Border(topPadding, rightPadding, bottomPadding, leftPadding), new Border(offset), textureFile);
	}
}
