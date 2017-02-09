package binnie.botany.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extrabees.ExtraBees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum BotanyTexture implements IBinnieTexture {
	;
	String texture;
	ResourceType type;

	BotanyTexture(final ResourceType base, final String texture) {
		this.texture = texture;
		this.type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		return Binnie.RESOURCE.getPNG(ExtraBees.instance, this.type, this.texture);
	}
}
