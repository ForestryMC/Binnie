package binnie.extratrees.models;

import binnie.core.block.TileEntityMetadata;
import binnie.core.models.AABBModelBaker;
import binnie.core.models.ModelManager;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.FenceType;
import forestry.api.core.IModelBaker;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.ModelBlockCached;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelMultiFence extends ModelBlockCached<BlockMultiFence, ModelMultiFence.Key> {

	public static final float POST_WIDGTH = 0.25F;
	public static final float POST_HEIGHT = 1F;
	public static final float SCALE = 0.0625f;
	
	public static class Key{
		private int meta;
		private FenceType type;
		boolean west;
		boolean east; 
		boolean north; 
		boolean south;
		public Key(int meta, boolean west, boolean east, boolean north, boolean south) {
			this.meta = meta;
			this.type = WoodManager.getFenceType(meta);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Key)){
				return false;
			}
			Key key = (Key) obj;
			return key.meta == meta && west == key.west && south == key.south &&north == key.north && east == key.east;
		}
	}
	
	public ModelMultiFence() {
		super(BlockMultiFence.class);
	}

	@Override
	protected Key getInventoryKey(ItemStack stack) {
		return new Key(TileEntityMetadata.getItemDamage(stack), false, false, false, false);
	}

	@Override
	protected Key getWorldKey(IBlockState state) {
		IExtendedBlockState stateExtended = (IExtendedBlockState) state;
		IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
		BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
		int meta = TileEntityMetadata.getTileMetadata(world, pos);
		return new Key(meta, state.getValue(BlockFence.WEST),state.getValue(BlockFence.EAST),state.getValue(BlockFence.NORTH),state.getValue(BlockFence.SOUTH));
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
		baker.setModelState(ModelManager.getDefaultFenceState());

		return itemModel = baker.bakeModel(true);
	}


	@Override
	protected void bakeBlock(BlockMultiFence block, Key key, IModelBaker baker, boolean inventory) {
		AABBModelBaker modelBaker = (AABBModelBaker) baker;
		if(inventory){
			bakeItemModel(block, modelBaker, key);
		}else{
			bakeBlockModel(block, modelBaker, key);
		}
	}
	
	private void bakeItemModel(BlockMultiFence block, AABBModelBaker modelBaker, Key key){
		int meta = key.meta;
		FenceType type = key.type;
		for (int i = 0; i < 5; ++i) {
			final float thickness = 0.125f;
			boolean secondary = false;
			if (i == 0) {
				modelBaker.setModelBounds(new AxisAlignedBB(0.5f - thickness, 0.0f, 0.0f, 0.5f + thickness, 1.0f, thickness * 2.0f));
			}
			if (i == 1) {
				modelBaker.setModelBounds(new AxisAlignedBB(0.5f - thickness, 0.0f, 1.0f - thickness * 2.0f, 0.5f + thickness, 1.0f, 1.0f));
			}
			final float s = 0.0625f;
			final boolean bottomBar = !type.solid;
			float topBarMaxY = 1.0f - s;
			float topBarMinY = 1.0f - s * 3.0f;
			float bottomBarMaxY = 0.5f - s;
			float bottomBarMinY = 0.5f - s * 3.0f;
			if (type.size == 2) {
				bottomBarMinY -= 4.0f * s;
				bottomBarMaxY -= 4.0f * s;
				topBarMinY -= 4.0f * s;
				topBarMaxY -= 4.0f * s;
			}
			if (type.size == 1) {
				bottomBarMinY -= 4.0f * s;
				bottomBarMaxY -= 4.0f * s;
			}
			if (type.solid) {
				topBarMinY = bottomBarMinY;
			}
			float minX = 0.5f - s;
			float maxX = 0.5f + s;
			float minZ = -s * 2.0f;
			float maxZ = 1.0f + s * 2.0f;
			if (i == 2) {
				modelBaker.setModelBounds(new AxisAlignedBB(minX, topBarMinY, minZ, maxX, topBarMaxY, maxZ));
				secondary = true;
			}
			if (i == 3) {
				if (!bottomBar) {
					continue;
				}
				modelBaker.setModelBounds(new AxisAlignedBB(minX, bottomBarMinY, minZ, maxX, bottomBarMaxY, maxZ));
				secondary = true;
			}
			if (i == 4) {
				if (type.embossed) {
					minX -= s * 0.9f;
					maxX += s * 0.9f;
					minZ -= s;
					maxZ += s;
					float minY = 0.0f;
					float maxY = 1.0f;
					if (type.size != 1 && !type.solid) {
						minY = bottomBarMinY + 2.0f * s;
						maxY = topBarMaxY - 2.0f * s;
					}
					else if (type.size == 1 && type.solid) {
						minY = bottomBarMinY + 2.0f * s;
						maxY = topBarMaxY - 2.0f * s;
					}
					else {
						minY = 0.5f - 2.0f * s;
						maxY = 0.5f + 2.0f * s;
					}
					if (type.solid && type.size == 0) {
						minY -= s;
						maxY -= s;
					}
					if (type.solid && type.size == 2) {
						minY += s;
						maxY += s;
					}
					modelBaker.setModelBounds(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
				}
				else {
					if (type.size != 1 || type.solid) {
						continue;
					}
					modelBaker.setModelBounds(new AxisAlignedBB(minX, 0.5f - s, minZ, maxX, 0.5f + s, maxZ));
					secondary = true;
				}
			}
			modelBaker.addBlockModel(null, block.getSprite(meta, secondary), 0);
		}
	}
	
	private void bakeBlockModel(BlockMultiFence block, AABBModelBaker modelBaker, Key key){
		float minPostPos = 0.5f - POST_WIDGTH / 2.0f;
		float maxPostPos = 0.5f + POST_WIDGTH / 2.0f;
		int meta = key.meta;
		FenceType fenceType = key.type;
		
		modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, 0.0, minPostPos, maxPostPos, POST_HEIGHT, maxPostPos));
		modelBaker.addBlockModel(null, block.getSprite(meta, false), 0);
		
		boolean connectNegX = key.west;
		boolean connectPosX = key.east;
		boolean connectNegZ = key.north;
		boolean connectPosZ = key.south;
		boolean connectAnyX = connectNegX || connectPosX;
		boolean connectAnyZ = connectNegZ || connectPosZ;
		if (!connectAnyX && !connectAnyZ) {
			connectAnyX = true;
		}
		minPostPos = 7.0f * SCALE;
		maxPostPos = 9.0f * SCALE;
		float barMinY = 12.0f * SCALE;
		float barMaxY = 15.0f * SCALE;
		float minX = connectNegX ? 0.0f : minPostPos;
		float maxX = connectPosX ? 1.0f : maxPostPos;
		float minZ = connectNegZ ? 0.0f : minPostPos;
		float maxZ = connectPosZ ? 1.0f : maxPostPos;
		boolean renderBottom = true;
		if (fenceType.size == 2) {
			barMaxY -= 5.0f * SCALE;
			barMinY -= 5.0f * SCALE;
		}
		if (fenceType.solid) {
			renderBottom = false;
			if (fenceType.size == 0) {
				barMinY = 6.0f * SCALE;
			}
			else {
				barMinY = SCALE;
			}
		}
		float totalMaxY = barMaxY;
		if (connectAnyX) {
			modelBaker.setModelBounds(new AxisAlignedBB(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos));
			modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
		}
		if (connectAnyZ) {
			modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ));
			modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
		}
		if (renderBottom) {
			barMinY -= 6.0f * SCALE;
			barMaxY -= 6.0f * SCALE;
			if (fenceType.size == 1) {
				barMinY += SCALE;
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
			barMinY -= 6.0f * SCALE;
			barMaxY -= 6.0f * SCALE;
			barMaxY += SCALE;
			if (connectAnyX) {
				modelBaker.setModelBounds(new AxisAlignedBB(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
			if (connectAnyZ) {
				modelBaker.setModelBounds(new AxisAlignedBB(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ));
				modelBaker.addBlockModel(null, block.getSprite(meta, true), 0);
			}
		}
		float totalMinY = barMinY;
		if (fenceType.embossed) {
			minPostPos -= (float) (SCALE - 0.25 * SCALE);
			maxPostPos += (float) (SCALE - 0.25 * SCALE);
			float minY = totalMinY + 2.0f * SCALE;
			float maxY = totalMaxY - 2.0f * SCALE;
			if (fenceType.size == 1 && !fenceType.solid) {
				minY = 6.0f * SCALE;
				maxY = 10.0f * SCALE;
			}
			else if (fenceType.size == 0 && fenceType.solid) {
				minY -= 4.0f * SCALE;
				maxY -= 4.0f * SCALE;
			}
			else if (fenceType.size == 2 && fenceType.solid) {
				minY += 4.0f * SCALE;
				maxY += 4.0f * SCALE;
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
		modelBaker.setParticleSprite(block.getSprite(meta, false));
	}
	
}
