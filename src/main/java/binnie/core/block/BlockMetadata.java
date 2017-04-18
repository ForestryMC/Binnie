package binnie.core.block;

import binnie.core.BinnieCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
	public BlockMetadata(final Material material) {
		super(material);
	}

	@Override
	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
		return getBlockDropped(this, world, x, y, z, blockMeta);
	}

	@Override
	// TODO fix deprecated usage
	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		return breakBlock(this, player, world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int i) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(final int meta) {
		return true;
	}

	@Override
	public boolean onBlockEventReceived(final World world, final int x, final int y, final int z, final int par5, final int par6) {
		super.onBlockEventReceived(world, x, y, z, par5, par6);
		final TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
	}

	@Override
	public IIcon getIcon(final IBlockAccess block, final int x, final int y, final int z, final int par5) {
		final int metadata = TileEntityMetadata.getTileMetadata(block, x, y, z);
		return this.getIcon(par5, metadata);
	}

	@Override
	public String getBlockName(final ItemStack itemStack) {
		return getLocalizedName();
	}

	@Override
	public void getBlockTooltip(final ItemStack itemStack, final List par3List) {
		// ignored
	}

	@Override
	public int getPlacedMeta(final ItemStack itemStack, final World world, final int x, final int y, final int z, final ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(itemStack);
	}

	@Override
	public int getDroppedMeta(final int tileMeta, final int blockMeta) {
		return tileMeta;
	}

	public static ArrayList<ItemStack> getBlockDropped(final IBlockMetadata block, final World world, final int x, final int y, final int z, final int blockMeta) {
		final ArrayList<ItemStack> array = new ArrayList<ItemStack>();
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
		if (tile != null && !tile.hasDroppedBlock()) {
			final int meta = block.getDroppedMeta(world.getBlockMetadata(x, y, z), tile.getTileMetadata());
			array.add(TileEntityMetadata.getItemStack((Block) block, meta));
		}
		return array;
	}

	public static boolean breakBlock(final IBlockMetadata block, final EntityPlayer player, final World world, final int i, final int j, final int k) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		final Block block2 = (Block) block;
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, i, j, k);
		if (tile != null && !tile.hasDroppedBlock()) {
			drops = block2.getDrops(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		}

		final boolean hasBeenBroken = world.setBlockToAir(i, j, k);
		if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (final ItemStack drop : drops) {
				block.dropAsStack(world, i, j, k, drop);
			}
			tile.dropBlock();
		}
		return hasBeenBroken;
	}

	@Override
	public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack itemStack) {
		this.dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		world.removeTileEntity(x, y, z);
	}

	public static ItemStack getPickBlock(final World world, final int x, final int y, final int z) {
		IBlockMetadata block = (IBlockMetadata) world.getBlock(x, y, z);
		List<ItemStack> list = getBlockDropped(block, world, x, y, z, world.getBlockMetadata(x, y, z));
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	// TODO fix deprecated usage
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
		return getPickBlock(world, x, y, z);
	}
}
