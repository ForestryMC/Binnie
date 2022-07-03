package binnie.core.block;

import binnie.core.BinnieCore;
import java.util.ArrayList;
import java.util.List;
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

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
    public BlockMetadata(Material material) {
        super(material);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        return getBlockDropped(this, world, x, y, z, blockMeta);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return breakBlock(this, player, world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityMetadata();
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int par5, int par6) {
        super.onBlockEventReceived(world, x, y, z, par5, par6);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(par5, par6);
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int par5) {
        int metadata = TileEntityMetadata.getTileMetadata(block, x, y, z);
        return getIcon(par5, metadata);
    }

    @Override
    public String getBlockName(ItemStack itemStack) {
        return getLocalizedName();
    }

    @Override
    public void addBlockTooltip(ItemStack itemStack, List tooltip) {
        // ignored
    }

    @Override
    public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
        return TileEntityMetadata.getItemDamage(itemStack);
    }

    @Override
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return blockMeta;
    }

    public static ArrayList<ItemStack> getBlockDropped(
            IBlockMetadata block, World world, int x, int y, int z, int blockMeta) {
        ArrayList<ItemStack> array = new ArrayList<>();
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null && !tile.hasDroppedBlock()) {
            int meta = block.getDroppedMeta(world.getBlockMetadata(x, y, z), tile.getTileMetadata());
            array.add(TileEntityMetadata.getItemStack((Block) block, meta));
        }
        return array;
    }

    public static boolean breakBlock(IBlockMetadata block, EntityPlayer player, World world, int i, int j, int k) {
        List<ItemStack> drops = new ArrayList<>();
        Block block2 = (Block) block;
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, i, j, k);
        if (tile != null && !tile.hasDroppedBlock()) {
            drops = block2.getDrops(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        }

        boolean hasBeenBroken = world.setBlockToAir(i, j, k);
        if (hasBeenBroken
                && BinnieCore.proxy.isSimulating(world)
                && drops.size() > 0
                && (player == null || !player.capabilities.isCreativeMode)) {
            for (ItemStack drop : drops) {
                block.dropAsStack(world, i, j, k, drop);
            }
            tile.dropBlock();
        }
        return hasBeenBroken;
    }

    @Override
    public void dropAsStack(World world, int x, int y, int z, ItemStack itemStack) {
        dropBlockAsItem(world, x, y, z, itemStack);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    public static ItemStack getPickBlock(World world, int x, int y, int z) {
        IBlockMetadata block = (IBlockMetadata) world.getBlock(x, y, z);
        List<ItemStack> list = getBlockDropped(block, world, x, y, z, world.getBlockMetadata(x, y, z));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return getPickBlock(world, x, y, z);
    }
}
