package binnie.core.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

class ParsedTextureSheet implements IBinnieTexture {
    private String name;
    private String modid;
    private String path;

    public ParsedTextureSheet(String name, String modid, String path) {
        this.name = name;
        this.modid = modid;
        this.path = path;
    }

    @Override
    public BinnieResource getTexture() {
        return new BinnieResource(modid, ResourceType.GUI, path);
    }
}
