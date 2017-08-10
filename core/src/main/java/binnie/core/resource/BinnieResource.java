package binnie.core.resource;

import net.minecraft.util.ResourceLocation;

import binnie.core.AbstractMod;

public class BinnieResource {
	String mod;
	String path;
	private ResourceType type;

	public BinnieResource(final AbstractMod mod, final ResourceType type, final String path) {
		this(mod.getModID(), type, path);
	}

	public BinnieResource(final String modid, final ResourceType type, final String path) {
		this.mod = modid;
		this.type = type;
		this.path = path;
	}

	public ResourceLocation getResourceLocation() {
		return this.path.endsWith(".png") ? new ResourceLocation(this.mod, "textures/" + this.type.toString() + "/" + this.path) : new ResourceLocation(this.mod, this.type.toString() + "/" + this.path);
	}
}
