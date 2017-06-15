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
	Gui(ResourceType.GUI, "gui"),
	Nursery(ResourceType.Tile, "Nursery"),
	Carpenter(ResourceType.Tile, "extratrees/carpenter_"),
	Paneler(ResourceType.Tile, "extratrees/paneler_"),
	Tileworker(ResourceType.Tile, "extratrees/tileworker_"),
	Incubator(ResourceType.Tile, "extratrees/incubator_"),
	Lumbermill(ResourceType.Tile, "extratrees/sawmill_"),
	Press(ResourceType.Tile, "extratrees/press_"),
	Distillery(ResourceType.Tile, "extratrees/distillery_"),
	Brewery(ResourceType.Tile, "extratrees/brewery_"),
	Infuser(ResourceType.Tile, "extratrees/infuser_");

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
