package binnie.core.gui.resource.stylesheet;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

class ParsedTextureSheet implements IBinnieTexture {
	private final String modid;
	private final String path;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	public ParsedTextureSheet(final String modid, final String path) {
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
