// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.resource.minecraft;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.resource.Texture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class StandardTexture extends Texture
{
	public StandardTexture(final int u, final int v, final int w, final int h, final IBinnieTexture textureFile) {
		this(u, v, w, h, 0, textureFile.getTexture());
	}

	public StandardTexture(final int u, final int v, final int w, final int h, final int offset, final IBinnieTexture textureFile) {
		this(u, v, w, h, offset, textureFile.getTexture());
	}

	public StandardTexture(final int u, final int v, final int w, final int h, final BinnieResource textureFile) {
		this(u, v, w, h, 0, textureFile);
	}

	public StandardTexture(final int u, final int v, final int w, final int h, final int padding, final BinnieResource textureFile) {
		super(new IArea(u, v, w, h), IBorder.ZERO, new IBorder(padding), textureFile);
	}

	public BinnieResource getTexture() {
		return getFilename();
	}
}
