package binnie.extratrees.block;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockETStairs extends BlockStairs implements IBlockMetadata {
    public BlockETStairs(Block par2Block) {
        super(par2Block, 0);
        setCreativeTab(Tabs.tabArboriculture);
        setBlockName("stairs");
        setResistance(5.0f);
        setHardness(2.0f);
        setStepSound(Block.soundTypeWood);
        useNeighborBrightness = true;
    }

    @Override
    public int getRenderType() {
        return ExtraTrees.stairsID;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < PlankType.ExtraTreePlanks.values().length; ++i) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return getIcon(side, tile.getTileMetadata());
        }
        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return WoodManager.getPlankType(meta + 32).getIcon();
    }

    @Override
    public void dropAsStack(World world, int x, int y, int z, ItemStack itemStack) {
        dropBlockAsItem(world, x, y, z, itemStack);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return BlockMetadata.breakBlock(this, player, world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMetadata();
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventType) {
        super.onBlockEventReceived(world, x, y, z, eventId, eventType);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(eventId, eventType);
    }

    @Override
    public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
        return TileEntityMetadata.getItemDamage(itemStack);
    }

    @Override
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(ItemStack itemStack) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        return I18N.localise(
                "extratrees.block.woodstairs.name",
                WoodManager.getPlankType(meta + 32).getName());
    }

    @Override
    public void addBlockTooltip(ItemStack itemStack, List tooltip) {
        // ignored
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
        super.breakBlock(world, x, y, z, block, side);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 20;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return true;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 5;
    }
}
