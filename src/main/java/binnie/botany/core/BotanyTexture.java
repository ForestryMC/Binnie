package binnie.botany.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extrabees.ExtraBees;

public enum BotanyTexture implements IBinnieTexture {
    ;
    String texture;
    ResourceType type;

    private BotanyTexture(final ResourceType base, final String texture) {
        this.texture = texture;
        this.type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(ExtraBees.instance, this.type, this.texture);
    }
}
