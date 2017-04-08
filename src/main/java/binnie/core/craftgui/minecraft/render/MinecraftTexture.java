package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.geometry.Area;
import binnie.core.resource.IBinnieTexture;

public class MinecraftTexture {
	Area textureArea;
	IBinnieTexture texture;

	public MinecraftTexture() {
		this.textureArea = new Area(0, 0, 0, 0);
	}
}
