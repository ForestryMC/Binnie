// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.resource.minecraft;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.resource.Texture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class PaddedTexture extends Texture
{
	public PaddedTexture(final int u, final int v, final int w, final int h, final int offset, final IBinnieTexture textureFile, final int leftPadding, final int rightPadding, final int topPadding, final int bottomPadding) {
		this(u, v, w, h, offset, textureFile.getTexture(), leftPadding, rightPadding, topPadding, bottomPadding);
	}

	public PaddedTexture(final int u, final int v, final int w, final int h, final int offset, final BinnieResource textureFile, final int leftPadding, final int rightPadding, final int topPadding, final int bottomPadding) {
		super(new IArea(u, v, w, h), new IBorder(topPadding, rightPadding, bottomPadding, leftPadding), new IBorder(offset), textureFile);
	}
}
