package binnie.extratrees.carpentry;

import binnie.core.block.BlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCarpentryPanel extends BlockCarpentry {
    public BlockCarpentryPanel() {
        useNeighborBrightness = true;
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        setLightOpacity(0);
    }

    public static boolean isValidPanelPlacement(World world, int x, int y, int z, ForgeDirection facing) {
        if (facing == ForgeDirection.UNKNOWN) {
            return false;
        }

        int bx = x - facing.offsetX;
        int by = y - facing.offsetY;
        int bz = z - facing.offsetZ;
        Block block = world.getBlock(bx, by, bz);
        return block != null && block.isSideSolid(world, bx, by, bz, facing);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        // ignored
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        DesignBlock block = getCarpentryBlock(world, x, y, z);
        switch (block.getFacing()) {
            case DOWN:
                setBlockBounds(0.0f, 0.9375f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;

            case EAST:
                setBlockBounds(0.0f, 0.0f, 0.0f, 0.0625f, 1.0f, 1.0f);
                break;

            case NORTH:
                setBlockBounds(0.0f, 0.0f, 0.9375f, 1.0f, 1.0f, 1.0f);
                break;

            case SOUTH:
                setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0625f);
                break;

            case UP:
                setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
                break;

            case WEST:
                setBlockBounds(0.9375f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }

    @Override
    public String getBlockName(ItemStack stack) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return I18N.localise(
                "extratrees.block.woodenpanel.name", block.getDesign().getName());
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return AxisAlignedBB.getBoundingBox(
                par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ);
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
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public DesignBlock getCarpentryBlock(IBlockAccess world, int x, int y, int z) {
        return ModuleCarpentry.getCarpentryPanel(getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
    }

    @Override
    public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection clickedBlock) {
        DesignBlock block =
                ModuleCarpentry.getCarpentryPanel(getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
        ForgeDirection facing = clickedBlock;
        boolean valid = true;
        if (!isValidPanelPlacement(world, x, y, z, facing)) {
            valid = false;
            for (ForgeDirection direction : ForgeDirection.values()) {
                if (isValidPanelPlacement(world, x, y, z, direction)) {
                    facing = direction;
                    valid = true;
                    break;
                }
            }
        }

        if (!valid) {
            return -1;
        }
        block.setFacing(facing);
        return block.getBlockMetadata(getDesignSystem());
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        DesignBlock carpentryBlock = getCarpentryBlock(world, x, y, z);
        if (isValidPanelPlacement(world, x, y, z, carpentryBlock.getFacing())) {
            return;
        }

        for (ItemStack stack : BlockMetadata.getBlockDropped(this, world, x, y, z, 0)) {
            dropBlockAsItem(world, x, y, z, stack);
        }
        world.setBlockToAir(x, y, z);
    }
}
