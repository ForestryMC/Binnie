package binnie.core.gui.resource.minecraft;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.resource.Texture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class StandardTexture extends Texture {
	public StandardTexture(final int u, final int v, final int w, final int h, final IBinnieTexture textureFile) {
		this(u, v, w, h, 0, textureFile);
	}

	public StandardTexture(final int u, final int v, final int w, final int h, final int padding, final IBinnieTexture textureFile) {
		super(new Area(u, v, w, h), Border.ZERO, new Border(padding), textureFile);
	}

	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		return this.getFilename();
	}
}
