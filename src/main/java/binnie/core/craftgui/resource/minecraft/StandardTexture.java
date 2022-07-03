package binnie.core.craftgui.resource.minecraft;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.resource.Texture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class StandardTexture extends Texture {
    public StandardTexture(int u, int v, int w, int h, IBinnieTexture textureFile) {
        this(u, v, w, h, 0, textureFile.getTexture());
    }

    public StandardTexture(int u, int v, int w, int h, int offset, IBinnieTexture textureFile) {
        this(u, v, w, h, offset, textureFile.getTexture());
    }

    public StandardTexture(int u, int v, int w, int h, BinnieResource textureFile) {
        this(u, v, w, h, 0, textureFile);
    }

    public StandardTexture(int u, int v, int w, int h, int padding, BinnieResource textureFile) {
        super(new IArea(u, v, w, h), IBorder.ZERO, new IBorder(padding), textureFile);
    }

    public BinnieResource getTexture() {
        return getFilename();
    }
}
