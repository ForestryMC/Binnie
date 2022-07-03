package binnie.extrabees.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extrabees.ExtraBees;

public enum ExtraBeeTexture implements IBinnieTexture {
    AlvearyMutator(ResourceType.Tile, "alveary/AlvearyMutator"),
    AlvearyNovaBlock(ResourceType.Tile, "alveary/AlvearyNovaBlock"),
    AlvearyFrame(ResourceType.Tile, "alveary/AlvearyFrame"),
    AlvearyLighting(ResourceType.Tile, "alveary/AlvearyLighting"),
    AlvearyRainShield(ResourceType.Tile, "alveary/AlvearyRainShield"),
    AlvearyStimulator(ResourceType.Tile, "alveary/AlvearyStimulator"),
    AlvearyHatchery(ResourceType.Tile, "alveary/AlvearyHatchery"),
    FX(ResourceType.FX, "fx"),
    GUIPunnett(ResourceType.GUI, "punnett"),
    GUIProgress(ResourceType.GUI, "processes"),
    GUIProgress2(ResourceType.GUI, "processes2"),
    AlvearyTransmission(ResourceType.Tile, "alveary/AlvearyTransmission");

    protected String texture;
    protected ResourceType type;

    ExtraBeeTexture(ResourceType base, String texture) {
        this.texture = texture;
        type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(ExtraBees.instance, type, texture);
    }
}
