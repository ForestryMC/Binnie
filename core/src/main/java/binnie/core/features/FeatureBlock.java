package binnie.core.features;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.AbstractMod;

public class FeatureBlock<B extends Block, I extends ItemBlock> extends FeatureBlockBased<B, I> {
	private final List<Consumer<B>> blockConsumers = new ArrayList<>();
	private final Supplier<B> blockSupplier;
	@Nullable
	protected B block;

	public FeatureBlock(IFeatureRegistry registry, String identifier, Supplier<B> blockSupplier, @Nullable Function<B, I> itemSupplier) {
		super(registry, identifier);
		this.blockSupplier = blockSupplier;

		if (itemSupplier != null) {
			setItem(new FeatureItemBlock<>(registry, identifier, () -> itemSupplier.apply(block)));
		}
		init();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends IForgeRegistryEntry<T>> void register(RegistryEvent.Register<T> event) {
		AbstractMod mod = registry.getMod();
		IForgeRegistry<T> registry = event.getRegistry();
		Class<T> superType = registry.getRegistrySuperType();
		if (Block.class.isAssignableFrom(superType) && block != null) {
			registry.register((T) block);
			mod.getProxy().onRegisterBlock(block);
		}
	}

	@Override
	public B block() {
		if (block == null) {
			throw new IllegalStateException("Called feature getter method before content creation.");
		}
		return block;
	}

	@Override
	protected void create() {
		block = blockSupplier.get();
		blockConsumers.forEach(consumer -> consumer.accept(block));
		blockConsumers.clear();
	}

	public FeatureBlock<B, I> onBlock(Consumer<B> blockConsumer) {
		blockConsumers.add(blockConsumer);
		return this;
	}
}
