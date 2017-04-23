// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.render;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.resource.IBinnieTexture;

public class MinecraftTexture
{
	IArea textureArea;
	IBinnieTexture texture;

	public MinecraftTexture() {
		textureArea = new IArea(0.0f, 0.0f, 0.0f, 0.0f);
	}
}
