package binnie.core.block;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
	public BlockMetadata(final Material material) {
		super(material);
	}

	public static ItemStack getBlockDropped(IBlockMetadata block, IBlockAccess world, BlockPos pos) {
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			final int meta = block.getDroppedMeta(world.getBlockState(pos), tile.getTileMetadata());
			return TileEntityMetadata.getItemStack((Block) block, meta);
		}
		return ItemStack.EMPTY;
	}

	public static void getDrops(NonNullList<ItemStack> drops, IBlockMetadata block, IBlockAccess world, BlockPos pos) {
		final ItemStack drop = getBlockDropped(block, world, pos);
		if (!drop.isEmpty()) {
			drops.add(drop);
		}
	}

	public static boolean breakBlock(IBlockMetadata blockMetadata, @Nullable EntityPlayer player, World world, BlockPos pos) {
		List<ItemStack> drops = null;
		final Block block = (Block) blockMetadata;
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			drops = block.getDrops(world, pos, world.getBlockState(pos), 0);
		}
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && !world.isRemote && drops != null && (player == null || !player.capabilities.isCreativeMode)) {
			for (ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
			tile.dropBlock();
		}
		return hasBeenBroken;
	}

	public static ItemStack getPickBlock(World world, BlockPos pos) {
		final Block block = world.getBlockState(pos).getBlock();
		return getBlockDropped((IBlockMetadata) block, world, pos);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		getDrops(drops, this, world, pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		return breakBlock(this, player, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return getPickBlock(world, pos);
	}

	/* IBLOCKMETADATA */
	@Override
	public String getDisplayName(final ItemStack itemStack) {
		return this.getLocalizedName();
	}

	@Override
	public int getPlacedMeta(final ItemStack item, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(item);
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		return getMetaFromState(state);
	}
}
