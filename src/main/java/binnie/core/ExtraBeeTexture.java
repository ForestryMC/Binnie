package binnie.core;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

public enum ExtraBeeTexture implements IBinnieTexture {
	AlvearyMutator(ResourceType.TILE, "alveary/AlvearyMutator"),
	AlvearyNovaBlock(ResourceType.TILE, "alveary/AlvearyNovaBlock"),
	AlvearyFrame(ResourceType.TILE, "alveary/AlvearyFrame"),
	AlvearyLighting(ResourceType.TILE, "alveary/AlvearyLighting"),
	AlvearyRainShield(ResourceType.TILE, "alveary/AlvearyRainShield"),
	AlvearyStimulator(ResourceType.TILE, "alveary/AlvearyStimulator"),
	AlvearyHatchery(ResourceType.TILE, "alveary/AlvearyHatchery"),
	FX(ResourceType.FX, "fx"),
	GUIProgress(ResourceType.GUI, "processes"),
	GUIProgress2(ResourceType.GUI, "processes2"),
	AlvearyTransmission(ResourceType.TILE, "alveary/AlvearyTransmission");

	private final String texture;
	private final ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	ExtraBeeTexture(final ResourceType base, final String texture) {
		this.texture = texture;
		this.type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG("extrabees", this.type, this.texture);
		}
		return resource;
	}
}
