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

//	public String getFullPath() {
//		return "/assets/" + this.mod + "/textures/" + this.type.toString() + "/" + this.path;
//	}

	public ResourceLocation getResourceLocation() {
		return this.path.endsWith(".png") ? new ResourceLocation(this.mod, "textures/" + this.type.toString() + "/" + this.path) : new ResourceLocation(this.mod, this.type.toString() + "/" + this.path);
	}

	public String getShortPath() {
		return "textures/" + this.type.toString() + "/" + this.path;
	}
}
