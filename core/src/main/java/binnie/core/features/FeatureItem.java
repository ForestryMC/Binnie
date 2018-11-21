package binnie.core.features;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FeatureItem<I extends Item> extends FeatureRegistered implements IFeatureItem<I> {
	private final Supplier<I> itemSupplier;
	@Nullable
	protected I item;

	public FeatureItem(IFeatureRegistry registry, String identifier, Supplier<I> itemSupplier) {
		super(registry, identifier);
		this.itemSupplier = itemSupplier;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IForgeRegistryEntry<T>> void register(RegistryEvent.Register<T> event) {
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (item != null && Item.class.isAssignableFrom(superType)) {
			registry.register((T) item);
			getMod().getProxy().onRegisterItem(item);
		}
	}

	@Override
	public <T extends IForgeRegistryEntry<T>> void afterRegistration(RegistryEvent.Register<T> event) {
		super.afterRegistration(event);
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (Item.class.isAssignableFrom(superType)) {
		}
	}

	@Override
	protected void create() {
		this.item = itemSupplier.get();
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	public I item() {
		if (item == null) {
			throw new IllegalStateException("Called feature getter method before content creation was called in the pre init.");
		}
		return item;
	}
}
