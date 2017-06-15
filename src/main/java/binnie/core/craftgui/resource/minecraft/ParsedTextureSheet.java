package binnie.core.craftgui.resource.minecraft;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

class ParsedTextureSheet implements IBinnieTexture {
	private String name;
	private String modid;
	private String path;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	public ParsedTextureSheet(final String name, final String modid, final String path) {
		this.name = name;
		this.modid = modid;
		this.path = path;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = new BinnieResource(this.modid, ResourceType.GUI, this.path);
		}
		return resource;
	}
}
