package binnie.core.modules;

import com.google.common.collect.ImmutableSet;
import forestry.api.modules.IForestryModule;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

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
