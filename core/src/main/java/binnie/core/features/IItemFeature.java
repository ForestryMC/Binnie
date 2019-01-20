package binnie.core.features;

import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;

public interface IItemFeature<O, I extends Item> extends IModFeature<O>, IItemProvider<I> {

	default I apply(I item) {
		return item;
	}

	void setItem(I item);

	default I item() {
		I item = getItem();
		if (item == null) {
			throw new IllegalStateException("Called feature getter method before content creation was called in the pre init.");
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	default <T extends IForgeRegistryEntry<T>> void register(RegistryEvent.Register<T> event) {
		AbstractMod mod = BinnieCore.getMod(getModId());
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (Item.class.isAssignableFrom(superType) && hasItem()) {
			registry.register((T) item());
			mod.getProxy().onRegisterItem(item());
		}
	}
}
