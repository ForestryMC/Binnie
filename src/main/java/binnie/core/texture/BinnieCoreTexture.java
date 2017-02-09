package binnie.core.texture;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public enum BinnieCoreTexture implements IBinnieTexture {
	Compartment(ResourceType.Tile, "Compartment"),
	CompartmentIron(ResourceType.Tile, "CompartmentIron"),
	CompartmentDiamond(ResourceType.Tile, "CompartmentDiamond"),
	CompartmentCopper(ResourceType.Tile, "CompartmentCopper"),
	CompartmentGold(ResourceType.Tile, "CompartmentGold"),
	CompartmentBronze(ResourceType.Tile, "CompartmentBronze"),
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
