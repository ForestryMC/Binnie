package binnie.core.block;

import forestry.api.core.IModelBaker;
import forestry.arboriculture.blocks.BlockForestryLeaves;
import forestry.arboriculture.models.ModelLeaves;
import forestry.core.models.ModelBlockCached;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ModelMutlipass<B extends Block & IMultipassBlock<K>, K> extends ModelBlockCached<B , K> {

	public ModelMutlipass(Class<B> blockClass) {
		super(blockClass);
	}

	@Override
	protected K getInventoryKey(ItemStack stack) {
		return ((B)Block.getBlockFromItem(stack.getItem())).getInventoryKey(stack);
	}

	@Override
	protected K getWorldKey(IBlockState state) {
		return ((B)state.getBlock()).getWorldKey(state);
	}

	@Override
	protected void bakeBlock(B block, K key, IModelBaker baker, boolean inventory) {
		for(int pass = 0;pass < block.getRenderPasses();pass++){
			baker.addBlockModel(block, Block.FULL_BLOCK_AABB, null, block.getSprite(key, pass), 0);
		}

		// Set the particle sprite
		baker.setParticleSprite(block.getSprite(key, -1));
	}

}
