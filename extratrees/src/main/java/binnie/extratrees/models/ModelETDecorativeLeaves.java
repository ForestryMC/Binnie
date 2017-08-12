package binnie.extratrees.models;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ILeafSpriteProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.core.IModelBaker;
import forestry.core.models.ModelBlockCached;
import forestry.core.models.baker.ModelBaker;
import forestry.core.proxy.Proxies;

import binnie.extratrees.block.BlockETDecorativeLeaves;
import binnie.extratrees.genetics.ETTreeDefinition;

@SideOnly(Side.CLIENT)
public class ModelETDecorativeLeaves extends ModelBlockCached<BlockETDecorativeLeaves, ETTreeDefinition> {
	public ModelETDecorativeLeaves() {
		super(BlockETDecorativeLeaves.class);
	}

	@Override
	protected ETTreeDefinition getInventoryKey(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if (!(block instanceof BlockETDecorativeLeaves)) {
			return null;
		}
		BlockETDecorativeLeaves bBlock = (BlockETDecorativeLeaves) block;
		return bBlock.getTreeType(stack.getMetadata());
	}

	@Override
	protected ETTreeDefinition getWorldKey(IBlockState state) {
		Block block = state.getBlock();
		if (!(block instanceof BlockETDecorativeLeaves)) {
			return null;
		}
		BlockETDecorativeLeaves bBlock = (BlockETDecorativeLeaves) block;
		return state.getValue(bBlock.getVariant());
	}

	@Override
	protected void bakeBlock(BlockETDecorativeLeaves block, ETTreeDefinition treeDefinition, IModelBaker baker, boolean inventory) {
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();

		ITreeGenome genome = treeDefinition.getGenome();
		IAlleleTreeSpecies species = genome.getPrimary();
		ILeafSpriteProvider leafSpriteProvider = species.getLeafSpriteProvider();

		ResourceLocation leafSpriteLocation = leafSpriteProvider.getSprite(false, Proxies.render.fancyGraphicsEnabled());
		TextureAtlasSprite leafSprite = map.getAtlasSprite(leafSpriteLocation.toString());

		// Render the plain leaf block.
		baker.addBlockModel(null, leafSprite, 0);

		// Render overlay for fruit leaves.
		ResourceLocation fruitSpriteLocation = genome.getFruitProvider().getDecorativeSprite();
		if (fruitSpriteLocation != null) {
			TextureAtlasSprite fruitSprite = map.getAtlasSprite(fruitSpriteLocation.toString());
			baker.addBlockModel(null, fruitSprite, 1);
		}

		// Set the particle sprite
		baker.setParticleSprite(leafSprite);
	}

	@Override
	protected IBakedModel bakeModel(IBlockState state, ETTreeDefinition key, BlockETDecorativeLeaves block) {
		if (key == null) {
			return null;
		}

		IModelBaker baker = new ModelBaker();

		if (!blockClass.isInstance(block)) {
			return null;
		}
		BlockETDecorativeLeaves bBlock = blockClass.cast(block);

		//baker.setRenderBounds(Block.FULL_BLOCK_AABB);
		bakeBlock(bBlock, key, baker, false);

		blockModel = baker.bakeModel(false);
		onCreateModel(blockModel);
		return blockModel;
	}
}