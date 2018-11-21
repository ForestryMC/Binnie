package binnie.core.features;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.AbstractMod;

public class FeatureItemBlock<I extends ItemBlock, B extends Block> extends FeatureBlockBased<B, I> {
	private final List<Consumer<I>> itemConsumers = new ArrayList<>();
	private final Supplier<I> itemSupplier;
	@Nullable
	protected I item;

	public FeatureItemBlock(IFeatureRegistry registry, String identifier, Supplier<I> itemSupplier) {
		super(registry, identifier);
		this.itemSupplier = itemSupplier;
		init();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends IForgeRegistryEntry<T>> void register(RegistryEvent.Register<T> event) {
		AbstractMod mod = registry.getMod();
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (Item.class.isAssignableFrom(superType) && item != null) {
			registry.register((T) item);
			mod.getProxy().onRegisterItem(item);
		}
	}

	@Override
	public I item() {
		if (item == null) {
			throw new IllegalStateException("Called feature getter method before content creation was called in the pre init.");
		}
		return item;
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	protected void create() {
		this.item = itemSupplier.get();
		itemConsumers.forEach(consumer -> consumer.accept(item));
		itemConsumers.clear();
	}

	public FeatureItemBlock<I, B> onItem(Consumer<I> itemConsumer) {
		itemConsumers.add(itemConsumer);
		return this;
	}
}
