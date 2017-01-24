package binnie.core.models;

import binnie.core.block.IMultipassBlock;
import forestry.api.core.IModelBaker;
import forestry.core.models.ModelBlockCached;
import forestry.core.models.baker.ModelBaker;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutlipass<B extends Block & IMultipassBlock<K>, K> extends ModelBlockCached<B, K> {

	public ModelMutlipass(Class<B> blockClass) {
		super(blockClass);
	}

	@Override
	protected K getInventoryKey(ItemStack stack) {
		return ((B) Block.getBlockFromItem(stack.getItem())).getInventoryKey(stack);
	}

	@Override
	protected K getWorldKey(IBlockState state) {
		return ((B) state.getBlock()).getWorldKey(state);
	}

	@Override
	protected void bakeBlock(B block, K key, IModelBaker baker, boolean inventory) {
		for (int pass = 0; pass < block.getRenderPasses(); pass++) {
			TextureAtlasSprite[] sprites = new TextureAtlasSprite[6];
			for(EnumFacing facing : EnumFacing.VALUES){
				sprites[facing.ordinal()] = block.getSprite(key, facing, pass);
			}
			baker.addBlockModel(block, Block.FULL_BLOCK_AABB, null, sprites, pass);
		}

		// Set the particle sprite
		//TODO: Adding a preaking sprite
		baker.setParticleSprite(block.getSprite(key, null, 0));
	}
	
	@Override
	protected IBakedModel bakeModel(ItemStack stack, World world, K key) {
		if (key == null) {
			return null;
		}

		IModelBaker baker = new ModelBaker();
		Block block = Block.getBlockFromItem(stack.getItem());
		if (!blockClass.isInstance(block)) {
			return null;
		}
		B bBlock = blockClass.cast(block);
		
		baker.setRenderBounds(Block.FULL_BLOCK_AABB);
		bakeBlock(bBlock, key, baker, true);

		return itemModel = baker.bakeModel(true);
	}

}
