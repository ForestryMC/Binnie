package binnie.core.texture;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public enum BinnieCoreTexture implements IBinnieTexture {
	GUI_BREEDING(ResourceType.GUI, "breeding"),
	GUI_ANALYST(ResourceType.GUI, "guianalyst"),
	GUI_PUNNETT(ResourceType.GUI, "punnett");

	private final String texture;
	private final ResourceType type;

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
