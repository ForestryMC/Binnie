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
	GUI(ResourceType.GUI, "gui"),
	NURSERY(ResourceType.TILE, "Nursery"),
	CARPENTER(ResourceType.TILE, "extratrees/carpenter_"),
	PANELER(ResourceType.TILE, "extratrees/paneler_"),
	TILEWORKER(ResourceType.TILE, "extratrees/tileworker_"),
	INCUBATOR(ResourceType.TILE, "extratrees/incubator_"),
	LUMBERMILL(ResourceType.TILE, "extratrees/sawmill_"),
	PRESS(ResourceType.TILE, "extratrees/press_"),
	DISTILLERY(ResourceType.TILE, "extratrees/distillery_"),
	BREWERY(ResourceType.TILE, "extratrees/brewery_"),
	INFUSER(ResourceType.TILE, "extratrees/infuser_");

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
