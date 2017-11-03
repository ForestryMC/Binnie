package binnie.core.modules;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraft.util.ResourceLocation;

import forestry.api.modules.IForestryModule;

public class BlankModule implements IForestryModule {

	private final ResourceLocation parent;

	public BlankModule(String container, String module) {
		this.parent = new ResourceLocation(container, module);
	}

	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return ImmutableSet.of(parent);
	}
}
