package binnie.extratrees.block.decor;

import binnie.core.block.TileEntityMetadata;
import binnie.core.models.AABBModelBaker;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.WoodManager;
import forestry.api.core.IModelBaker;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.ModelBlockDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelMultiFence extends ModelBlockDefault<BlockMultiFence, ModelMultiFence.Key> {

	public static class Key{
		private int meta;
		private FenceType type;
		private IBlockState state;
		public Key(int meta, IBlockState state) {
			this.meta = meta;
			this.type = WoodManager.getFenceType(meta);
			this.state = state;
		}
	}
	
	public ModelMultiFence() {
		super(BlockMultiFence.class);
	}

	@Override
	protected Key getInventoryKey(ItemStack stack) {
		return new Key(stack.getItemDamage(), ExtraTrees.blocks().blockMultiFence.getDefaultState());
	}

	@Override
	protected Key getWorldKey(IBlockState state) {
		IExtendedBlockState stateExtended = (IExtendedBlockState) state;
		IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
		BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
		TileEntity tileEntity = world.getTileEntity(pos);
		int meta;
		if(tileEntity instanceof TileEntityMetadata){
			TileEntityMetadata tileMeta = (TileEntityMetadata) tileEntity;
			meta = tileMeta.getBlockMetadata();
		}else{
			meta = 0;
		}
		return new Key(meta, stateExtended);
	}
	
	@Override
	protected IBakedModel bakeModel(IBlockState state, Key key, BlockMultiFence block) {
		if (key == null) {
			return null;
		}

		AABBModelBaker baker = new AABBModelBaker();
		bakeBlock(block, key, baker, false);

		blockModel = baker.bakeModel(false);
		onCreateModel(blockModel);
		return blockModel;
	}

	@Override
	protected IBakedModel bakeModel(ItemStack stack, World world, Key key) {
		if (key == null) {
			return null;
		}

		Block block = Block.getBlockFromItem(stack.getItem());
		if (!blockClass.isInstance(block)) {
			return null;
		}
		BlockMultiFence bBlock = blockClass.cast(block);


		IModelBaker baker = new AABBModelBaker();
		bakeBlock(bBlock, key, baker, true);

		return itemModel = baker.bakeModel(true);
	}


	@Override
	protected void bakeBlock(BlockMultiFence block, Key key, IModelBaker baker, boolean inventory) {
		AABBModelBaker modelBaker = (AABBModelBaker) baker;
		final float scale = 0.0625f;
		IBlockState state = key.state;
		int meta = key.meta;
		FenceType fenceType = key.type;
		final float postWidth = 0.25f;
		final float postHeight = 1.0f;
		float minPostPos = 0.5f - postWidth / 2.0f;
		float maxPostPos = 0.5f + postWidth / 2.0f;
		modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, 0.0, minPostPos, maxPostPos, postHeight, maxPostPos));
		modelBaker.addBlockModel(null, block.getSprite(meta, false), 0);
		final boolean connectNegX = state.getValue(net.minecraft.block.BlockFence.WEST);
		final boolean connectPosX = state.getValue(net.minecraft.block.BlockFence.EAST);
		final boolean connectNegZ = state.getValue(net.minecraft.block.BlockFence.NORTH);
		final boolean connectPosZ = state.getValue(net.minecraft.block.BlockFence.SOUTH);
		boolean connectAnyX = connectNegX || connectPosX;
		boolean connectAnyZ = connectNegZ || connectPosZ;
		if (!connectAnyX && !connectAnyZ) {
			connectAnyX = true;
		}
		minPostPos = 7.0f * scale;
		maxPostPos = 9.0f * scale;
		float barMinY = 12.0f * scale;
		float barMaxY = 15.0f * scale;
		final float minX = connectNegX ? 0.0f : minPostPos;
		final float maxX = connectPosX ? 1.0f : maxPostPos;
		final float minZ = connectNegZ ? 0.0f : minPostPos;
		final float maxZ = connectPosZ ? 1.0f : maxPostPos;
		boolean renderBottom = true;
		if (fenceType.size == 2) {
			barMaxY -= 5.0f * scale;
			barMinY -= 5.0f * scale;
		}
		if (fenceType.solid) {
			renderBottom = false;
			if (fenceType.size == 0) {
				barMinY = 6.0f * scale;
			}
			else {
				barMinY = scale;
			}
		}
		final float totalMaxY = barMaxY;
		if (connectAnyX) {
			modelBaker.setModelBounds(new AxisAlignedBB(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos));
			modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
		}
		if (connectAnyZ) {
			modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ));
			modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
		}
		if (renderBottom) {
			barMinY -= 6.0f * scale;
			barMaxY -= 6.0f * scale;
			if (fenceType.size == 1) {
				barMinY += scale;
			}
			if (connectAnyX) {
				modelBaker.setModelBounds(new AxisAlignedBB(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
			if (connectAnyZ) {
				modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
		}
		if (renderBottom && fenceType.size == 1) {
			barMinY -= 6.0f * scale;
			barMaxY -= 6.0f * scale;
			barMaxY += scale;
			if (connectAnyX) {
				modelBaker.setModelBounds(new AxisAlignedBB(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
			if (connectAnyZ) {
				modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
		}
		final float totalMinY = barMinY;
		if (fenceType.embossed) {
			minPostPos -= (float) (scale - 0.25 * scale);
			maxPostPos += (float) (scale - 0.25 * scale);
			float minY = totalMinY + 2.0f * scale;
			float maxY = totalMaxY - 2.0f * scale;
			if (fenceType.size == 1 && !fenceType.solid) {
				minY = 6.0f * scale;
				maxY = 10.0f * scale;
			}
			else if (fenceType.size == 0 && fenceType.solid) {
				minY -= 4.0f * scale;
				maxY -= 4.0f * scale;
			}
			else if (fenceType.size == 2 && fenceType.solid) {
				minY += 4.0f * scale;
				maxY += 4.0f * scale;
			}
			if (connectAnyX) {
				modelBaker.setModelBounds(new AxisAlignedBB(minX, minY, minPostPos, maxX, maxY, maxPostPos));
				modelBaker.addBlockModel(null, block.getSprite(meta, false), 0);
			}
			if (connectAnyZ) {
				modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, minY, minZ, maxPostPos, maxY, maxZ));
				modelBaker.addBlockModel(null, block.getSprite(meta, false), 0);
			}
		}
	}
	
}
