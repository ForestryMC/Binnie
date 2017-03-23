package binnie.craftgui.minecraft.render;

import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.Area;

public class MinecraftTexture {
	Area textureArea;
	IBinnieTexture texture;

	public MinecraftTexture() {
		this.textureArea = new Area(0, 0, 0, 0);
	}
}
