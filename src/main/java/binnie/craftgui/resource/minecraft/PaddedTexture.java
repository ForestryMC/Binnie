package binnie.craftgui.resource.minecraft;

import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.resource.Texture;

public class PaddedTexture extends Texture {
	public PaddedTexture(final int u, final int v, final int w, final int h, final int offset, final IBinnieTexture textureFile, final int leftPadding, final int rightPadding, final int topPadding, final int bottomPadding) {
		super(new IArea(u, v, w, h), new IBorder(topPadding, rightPadding, bottomPadding, leftPadding), new IBorder(offset), textureFile);
	}
}
