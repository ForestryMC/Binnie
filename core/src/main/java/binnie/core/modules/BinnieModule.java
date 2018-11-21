package binnie.core.modules;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraft.util.ResourceLocation;

import forestry.api.modules.IForestryModule;

public class BinnieModule implements IForestryModule {

	private final ResourceLocation parent;

	public BinnieModule(String container, String module) {
		this.parent = new ResourceLocation(container, module);
	}

	public void onFeatureCreation() {
	}

	public void afterFeatureRegistartion() {

	}

	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return ImmutableSet.of(parent);
	}
}
