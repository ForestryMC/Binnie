package binnie.extratrees.block;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockETLog extends BlockLog implements IBlockMetadata {
    public BlockETLog() {
        setCreativeTab(Tabs.tabArboriculture);
        setBlockName("log");
        setResistance(5.0f);
        setHardness(2.0f);
        setStepSound(Block.soundTypeWood);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < ILogType.ExtraTreeLog.values().length; ++i) {
            list.add(TileEntityMetadata.getItemStack(this, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return getIcon(side, tile.getTileMetadata(), world.getBlockMetadata(x, y, z));
        }
        return super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(int side, int tileMeta, int blockMeta) {
        int oriented = blockMeta & 0xC;
        ILogType.ExtraTreeLog log = ILogType.ExtraTreeLog.values()[tileMeta];
        switch (oriented) {
            case 4:
                if (side > 3) {
                    return log.getTrunk();
                }
                return log.getBark();

            case 8:
                if (side == 2 || side == 3) {
                    return log.getTrunk();
                }
                return log.getBark();
        }
        if (side < 2) {
            return log.getTrunk();
        }
        return log.getBark();
    }

    @Override
    public IIcon getIcon(int side, int tileMeta) {
        return getIcon(side, tileMeta, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        ILogType.ExtraTreeLog.registerIcons(register);
    }

    @Override
    public int getRenderType() {
        return 31;
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
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(ItemStack itemStack) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        ILogType.ExtraTreeLog[] logs = ILogType.ExtraTreeLog.values();
        if (meta < 0 || meta >= logs.length) {
            meta = 0;
        }
        return I18N.localise("extratrees.block.log.name", logs[meta].getName());
    }

    @Override
    public void addBlockTooltip(ItemStack itemStack, List tooltip) {
        // ignored
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
        return TileEntityMetadata.getItemDamage(itemStack);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float par6, float par7, float par8, int meta) {
        byte b0 = 0;
        switch (side) {
            case 0:
            case 1:
                b0 = 0;
                break;

            case 2:
            case 3:
                b0 = 8;
                break;

            case 4:
            case 5:
                b0 = 4;
                break;
        }
        return b0;
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

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }
}
