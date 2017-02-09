package binnie.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.resource.Texture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StandardTexture extends Texture {
	public StandardTexture(final int u, final int v, final int w, final int h, final IBinnieTexture textureFile) {
		this(u, v, w, h, 0, textureFile);
	}

	public StandardTexture(final int u, final int v, final int w, final int h, final int padding, final IBinnieTexture textureFile) {
		super(new IArea(u, v, w, h), IBorder.ZERO, new IBorder(padding), textureFile);
	}

	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		return this.getFilename();
	}
}
