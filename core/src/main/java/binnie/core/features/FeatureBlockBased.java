package binnie.core.features;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class FeatureBlockBased<B extends Block, I extends ItemBlock> extends FeatureRegistered implements IFeatureItem<I> {
	@Nullable
	private FeatureBlockBased<B, I> item;
	@Nullable
	private FeatureBlockBased<B, I> block;

	public FeatureBlockBased(IFeatureRegistry registry, String identifier) {
		super(registry, identifier);
	}

	protected void init() {
		if (this instanceof FeatureItemBlock) {
			item = this;
		} else if (this instanceof FeatureBlock) {
			block = this;
		} else if (block != null && block.item != null && item == null) {
			item = block.item;
		}
	}

	public void setItem(FeatureBlockBased<B, I> item) {
		this.item = item;
		addChild(item);
	}

	public void setBlock(FeatureBlockBased<B, I> block) {
		this.block = block;
		addChild(block);
	}

	public FeatureBlockBased<B, I> onBlock(Consumer<B> blockConsumer) {
		if (block != null) {
			block.onBlock(blockConsumer);
		}
		return this;
	}

	public FeatureBlockBased<B, I> onItem(Consumer<I> itemConsumer) {
		if (item != null) {
			item.onItem(itemConsumer);
		}
		return this;
	}

	public I item() {
		if (item == null) {
			throw new NullPointerException("No item present for this block.");
		}
		return item.item();
	}

	public boolean hasItem() {
		return item != null;
	}

	public Optional<I> maybeItem() {
		if (item == null) {
			return Optional.empty();
		}
		return Optional.of(item());
	}

	public B block() {
		if (block == null) {
			throw new NullPointerException("No block present for this item.");
		}
		return block.block();
	}

	public boolean hasBlock() {
		return block != null;
	}

	public Optional<B> maybeBlock() {
		if (block == null) {
			return Optional.empty();
		}
		return Optional.of(block());
	}
}
