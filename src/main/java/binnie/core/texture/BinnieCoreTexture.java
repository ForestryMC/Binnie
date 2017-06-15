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
	Compartment(ResourceType.Tile, "compartment"),
	CompartmentIron(ResourceType.Tile, "compartment_iron"),
	CompartmentDiamond(ResourceType.Tile, "compartment_diamond"),
	CompartmentCopper(ResourceType.Tile, "compartment_copper"),
	CompartmentGold(ResourceType.Tile, "compartment_gold"),
	CompartmentBronze(ResourceType.Tile, "compartment_bronze"),
	GUIBreeding(ResourceType.GUI, "breeding"),
	GUIAnalyst(ResourceType.GUI, "guianalyst");

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
