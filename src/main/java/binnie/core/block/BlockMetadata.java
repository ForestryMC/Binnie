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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
    static int temporyMeta;

    public BlockMetadata(final Material material) {
        super(material);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return getBlockDropped(this, world, pos, state.getBlock().getMetaFromState(state));
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


//	@Override
//	public boolean onBlockEventReceived(final World par1World, final BlockPos pos, final int par5, final int par6) {
//		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
//		final TileEntity tileentity = par1World.getTileEntity(pos);
//		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
//	}
//
//	@Override
//	public IIcon getIcon(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
//		final int metadata = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
//		return this.getIcon(par5, metadata);
//	}

    @Override
    public String getBlockName(final ItemStack par1ItemStack) {
        return this.getLocalizedName();
    }

    @Override
    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    @Override
    public int getPlacedMeta(final ItemStack item, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
        final int damage = TileEntityMetadata.getItemDamage(item);
        return damage;
    }

    @Override
    public int getDroppedMeta(final int tileMeta, final int blockMeta) {
        return tileMeta;
    }

    public static ArrayList<ItemStack> getBlockDropped(final IBlockMetadata block, final IBlockAccess world, final BlockPos pos, final int blockMeta) {
        final ArrayList<ItemStack> array = new ArrayList<>();
        final TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
        if (tile != null && !tile.hasDroppedBlock()) {
            //final int meta = block.getDroppedMeta(world.getBlockMetadata(x, y, z), tile.getTileMetadata());
            //array.add(TileEntityMetadata.getItemStack((Block) block, meta));
        }
        return array;
    }

    public static boolean breakBlock(final IBlockMetadata block, final EntityPlayer player, final World world, final BlockPos pos) {
        List<ItemStack> drops = new ArrayList<>();
        final Block block2 = (Block) block;
        final TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
        if (tile != null && !tile.hasDroppedBlock()) {
            final int tileMeta = TileEntityMetadata.getTileMetadata(world, pos);
            //drops = block2.getDrops(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        }
        //final boolean hasBeenBroken = world.setBlockToAir(i, j, k);
//		if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
//			for (final ItemStack drop : drops) {
//				//block.dropAsStack(world, i, j, k, drop);
//			}
//			tile.dropBlock();
//		}
        return false; //hasBeenBroken;
    }

    @Override
    public void dropAsStack(final World world, BlockPos pos, final ItemStack drop) {
        //this.dropBlockAsItem(world, pos, drop);
    }

//	@Override
//	public void breakBlock(final World par1World, final BlockPos pos, final Block par5, final int par6) {
////		super.breakBlock(par1World, par2, par3, par4, par5, par6);
////		par1World.removeTileEntity(par2, par3, par4);
//	}

    //
    //TODO DROP
    public static ItemStack getPickBlock(final World world, final BlockPos pos) {
        //final List<ItemStack> list = getBlockDropped((IBlockMetadata) world.getBlockState(pos).getBlock(), world, pos, world.getBlockMetadata(x, y, z));
        //return list.isEmpty() ? null : list.get(0);
        return null;
    }
//	@Override
//	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
//		return getPickBlock(world, x, y, z);
//	}

    static {
        BlockMetadata.temporyMeta = -1;
    }
}
