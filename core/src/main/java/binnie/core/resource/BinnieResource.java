package binnie.core.resource;

import net.minecraft.util.ResourceLocation;

import binnie.core.AbstractMod;

public class BinnieResource {
	private final String mod;
	private final String path;
	private final ResourceType type;

	public BinnieResource(final AbstractMod mod, final ResourceType type, final String path) {
		this(mod.getModId(), type, path);
	}

	public BinnieResource(final String modid, final ResourceType type, final String path) {
		this.mod = modid;
		this.type = type;
		this.path = path;
	}

	public ResourceLocation getResourceLocation() {
		if (this.path.endsWith(".png")) {
			return new ResourceLocation(this.mod, "textures/" + this.type.toString() + '/' + this.path);
		} else {
			return new ResourceLocation(this.mod, this.type.toString() + '/' + this.path);
		}
	}
}
