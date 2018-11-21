package binnie.core.features.integration;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.common.Loader;

import binnie.core.features.FeatureRegistered;
import binnie.core.features.IFeatureItem;
import binnie.core.features.IFeatureRegistry;

public class ModItem<I extends Item> extends FeatureRegistered implements IFeatureItem<I> {

	@Nullable
	private I item;

	public ModItem(IFeatureRegistry registry, String modId, String identifier) {
		super(registry, modId, identifier);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IForgeRegistryEntry<T>> void afterRegistration(RegistryEvent.Register<T> event) {
		super.afterRegistration(event);
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (Item.class.isAssignableFrom(superType)) {
			item = (I) registry.getValue(registryName);
		}
	}

	@Override
	public boolean isEnabled() {
		return Loader.isModLoaded(registryName.getResourceDomain());
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	public I item() {
		if (item == null) {
			throw new IllegalStateException("Called feature getter method before the init phase.");
		}
		return item;
	}
}
