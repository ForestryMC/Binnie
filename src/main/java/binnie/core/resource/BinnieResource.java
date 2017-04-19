package binnie.core.resource;

import binnie.core.AbstractMod;
import net.minecraft.util.ResourceLocation;

public class BinnieResource {
	String mod;
	private ResourceType type;
	String path;

	public BinnieResource(final AbstractMod mod, final ResourceType type, final String path) {
		this(mod.getModID(), type, path);
	}

	public BinnieResource(final String modid, final ResourceType type, final String path) {
		this.mod = modid;
		this.type = type;
		this.path = path;
	}

	// TODO unused method?
	public String getFullPath() {
		return "/assets/" + mod + "/textures/" + type.toString() + "/" + path;
	}

	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(mod, "textures/" + type.toString() + "/" + path);
	}

	public String getShortPath() {
		return "textures/" + type.toString() + "/" + path;
	}
}
