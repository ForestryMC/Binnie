package binnie.craftgui.minecraft.render;

import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.IArea;

public class MinecraftTexture {
    IArea textureArea;
    IBinnieTexture texture;

    public MinecraftTexture() {
        this.textureArea = new IArea(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
