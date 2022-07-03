package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStained extends Block implements IBlockMetadata {
    public BlockStained() {
        super(Material.glass);
        setCreativeTab(CreativeTabBotany.instance);
        setBlockName("stained");
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        //		Block block2 = world.getBlock(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z -
        // Facing.offsetsZForSide[side]);
        Block block = world.getBlock(x, y, z);
        return block != this && block != ExtraTrees.blockStained && super.shouldSideBeRendered(world, x, y, z, side);
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
    public TileEntity createNewTileEntity(World var1, int i) {
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
        return I18N.localise("botany.pigmentedGlass");
    }

    @Override
    public void addBlockTooltip(ItemStack itemStack, List tooltip) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        tooltip.add(EnumChatFormatting.GRAY + EnumFlowerColor.get(meta).getName());
    }

    @Override
    public void dropAsStack(World world, int x, int y, int z, ItemStack stack) {
        dropBlockAsItem(world, x, y, z, stack);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List itemList) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
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
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = Botany.proxy.getIcon(register, "stained");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return getRenderColor(tile.getTileMetadata());
        }
        return 0xffffff;
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

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return EnumFlowerColor.get(meta).getColor(false);
    }
}
