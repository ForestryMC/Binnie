package binnie.core.texture;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

public enum BinnieCoreTexture implements IBinnieTexture {
	COMPARTMENT(ResourceType.Tile, "compartment"),
	COMPARTMENT_IRON(ResourceType.Tile, "compartment_iron"),
	COMPARTMENT_DIAMOND(ResourceType.Tile, "compartment_diamond"),
	COMPARTMENT_COPPER(ResourceType.Tile, "compartment_copper"),
	COMPARTMENT_GOLD(ResourceType.Tile, "compartment_gold"),
	COMPARTMENT_BRONZE(ResourceType.Tile, "compartment_bronze"),
	GUI_BREEDING(ResourceType.GUI, "breeding"),
	GUI_ANALYST(ResourceType.GUI, "guianalyst"),
	GUI_PUNNETT(ResourceType.GUI, "punnett");

	String texture;
	ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	BinnieCoreTexture(final ResourceType base, final String texture) {
		this.texture = texture;
		this.type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(BinnieCore.getInstance(), this.type, this.texture);
		}
		return resource;
	}
}
