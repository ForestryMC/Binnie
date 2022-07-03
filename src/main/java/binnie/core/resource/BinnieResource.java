package binnie.core.resource;

import binnie.core.AbstractMod;
import net.minecraft.util.ResourceLocation;

public class BinnieResource {
    String mod;
    private ResourceType type;
    String path;

    public BinnieResource(AbstractMod mod, ResourceType type, String path) {
        this(mod.getModID(), type, path);
    }

    public BinnieResource(String modid, ResourceType type, String path) {
        mod = modid;
        this.type = type;
        this.path = path;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(mod, "textures/" + type.toString() + "/" + path);
    }

    public String getShortPath() {
        return "textures/" + type.toString() + "/" + path;
    }
}
