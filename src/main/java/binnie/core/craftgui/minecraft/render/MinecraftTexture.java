package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.resource.IBinnieTexture;

// TODO unused class?
public class MinecraftTexture {
	protected IArea textureArea;
	protected IBinnieTexture texture;

	public MinecraftTexture() {
		textureArea = new IArea(0.0f, 0.0f, 0.0f, 0.0f);
	}
}
