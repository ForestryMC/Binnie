package binnie.core.craftgui.resource.minecraft;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.resource.Texture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class PaddedTexture extends Texture {
    public PaddedTexture(
            int u,
            int v,
            int w,
            int h,
            int offset,
            IBinnieTexture textureFile,
            int leftPadding,
            int rightPadding,
            int topPadding,
            int bottomPadding) {
        this(u, v, w, h, offset, textureFile.getTexture(), leftPadding, rightPadding, topPadding, bottomPadding);
    }

    public PaddedTexture(
            int u,
            int v,
            int w,
            int h,
            int offset,
            BinnieResource textureFile,
            int leftPadding,
            int rightPadding,
            int topPadding,
            int bottomPadding) {
        super(
                new IArea(u, v, w, h),
                new IBorder(topPadding, rightPadding, bottomPadding, leftPadding),
                new IBorder(offset),
                textureFile);
    }
}
