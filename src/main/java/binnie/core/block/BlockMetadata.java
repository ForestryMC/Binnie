package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import binnie.core.BinnieCore;

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
	static int temporyMeta = -1;

	public BlockMetadata(final Material material) {
		super(material);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return getBlockDroppedAsList(this, world, pos);
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
	public String getDisplayName(final ItemStack par1ItemStack) {
		return this.getLocalizedName();
	}

	@Override
	public int getPlacedMeta(final ItemStack item, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		final int damage = TileEntityMetadata.getItemDamage(item);
		return damage;
	}
	
	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		return getMetaFromState(state);
	}
	
	public static ItemStack getBlockDropped(IBlockMetadata block, IBlockAccess world, BlockPos pos) {
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			int meta = block.getDroppedMeta(world.getBlockState(pos), tile.getTileMetadata());
			return TileEntityMetadata.getItemStack((Block) block, meta);
		}
		return null;
	}

	public static List<ItemStack> getBlockDroppedAsList(IBlockMetadata block, IBlockAccess world, BlockPos pos) {
		ItemStack drop = getBlockDropped(block, world, pos);
		if(drop != null){
			return Collections.singletonList(getBlockDropped(block, world, pos));
		}
		return Collections.emptyList();
	}

	public static boolean breakBlock(IBlockMetadata blockMetadata, EntityPlayer player, World world, BlockPos pos) {
		List<ItemStack> drops = new ArrayList<>();
		Block block = (Block) blockMetadata;
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			drops = block.getDrops(world, pos, world.getBlockState(pos), 0);
		}
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for(ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
			tile.dropBlock();
		}
		return hasBeenBroken;
	}

	public static ItemStack getPickBlock(World world, BlockPos pos) {
		return getBlockDropped((IBlockMetadata) world.getBlockState(pos).getBlock(), world, pos);
	}

}
