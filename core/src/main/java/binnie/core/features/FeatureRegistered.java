package binnie.core.features;

import net.minecraft.util.ResourceLocation;

public class FeatureRegistered extends Feature {
	protected final ResourceLocation registryName;

	public FeatureRegistered(IFeatureRegistry registry, String modId, String identifier) {
		super(registry, identifier);
		this.registryName = new ResourceLocation(modId, identifier);
	}

	public FeatureRegistered(IFeatureRegistry registry, String identifier) {
		this(registry, registry.getModId(), identifier);
	}

	public ResourceLocation getRegistryName() {
		return registryName;
	}
}
