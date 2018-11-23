package binnie.core.gui.resource.textures;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.resource.IBinnieTexture;

public class StandardTexture extends Texture {
	public StandardTexture(int u, int v, int w, int h, IBinnieTexture textureFile) {
		this(u, v, w, h, 0, textureFile);
	}

	public StandardTexture(int u, int v, int w, int h, int padding, IBinnieTexture textureFile) {
		super(new Area(u, v, w, h), Border.ZERO, new Border(padding), textureFile);
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture() {
		return this.getResourceLocation();
	}
}
