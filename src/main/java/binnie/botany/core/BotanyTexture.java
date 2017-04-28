package binnie.botany.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extrabees.ExtraBees;

// TODO unused class?
public enum BotanyTexture implements IBinnieTexture {
	;

	protected String texture;
	protected ResourceType type;

	BotanyTexture(ResourceType base, String texture) {
		this.texture = texture;
		type = base;
	}

	@Override
	public BinnieResource getTexture() {
		return Binnie.Resource.getPNG(ExtraBees.instance, type, texture);
	}
}
