package binnie.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

class ParsedTextureSheet implements IBinnieTexture {
    private String name;
    private String modid;
    private String path;

    public ParsedTextureSheet(final String name, final String modid, final String path) {
        this.name = name;
        this.modid = modid;
        this.path = path;
    }

    @Override
    public BinnieResource getTexture() {
        return new BinnieResource(this.modid, ResourceType.GUI, this.path);
    }
}
