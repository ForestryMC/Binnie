package binnie.extratrees.core;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeTexture implements IBinnieTexture {
	GUI(ResourceType.GUI, "gui");

	private final String texture;
	private final ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	ExtraTreeTexture(final ResourceType base, final String texture) {
		this.texture = texture;
		this.type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(ExtraTrees.instance, this.type, this.texture);
		}
		return resource;
	}

}
