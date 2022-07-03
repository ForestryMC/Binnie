package binnie.extratrees.block.decor;

import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.genetics.LeafType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.ForestryAPI;
import forestry.api.core.Tabs;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHedge extends Block implements IBlockFence {
    public BlockHedge() {
        super(Material.leaves);
        setCreativeTab(Tabs.tabArboriculture);
        setBlockName("hedge");
    }

    public static int getColor(int meta) {
        LeafType type = LeafType.values()[meta % 6];
        if (type == LeafType.CONIFER) {
            return ColorizerFoliage.getFoliageColorPine();
        }
        double d0 = 0.5;
        double d2 = 1.0;
        return ColorizerFoliage.getFoliageColor(d0, d2);
    }

    @Override
    public void addCollisionBoxesToList(
            World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
        boolean connectNegZ = canConnectFenceTo(world, x, y, z - 1);
        boolean connectPosZ = canConnectFenceTo(world, x, y, z + 1);
        boolean connectNegX = canConnectFenceTo(world, x - 1, y, z);
        boolean connectPosX = canConnectFenceTo(world, x + 1, y, z);
        float f = 0.25f;
        float f2 = 0.75f;
        float f3 = 0.25f;
        float f4 = 0.75f;
        if (connectNegZ) {
            f3 = 0.0f;
        }
        if (connectPosZ) {
            f4 = 1.0f;
        }
        if (connectNegZ || connectPosZ) {
            setBlockBounds(f, 0.0f, f3, f2, 1.5f, f4);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }

        f3 = 0.25f;
        f4 = 0.75f;
        if (connectNegX) {
            f = 0.0f;
        }
        if (connectPosX) {
            f2 = 1.0f;
        }
        if (connectNegX || connectPosX || (!connectNegZ && !connectPosZ)) {
            setBlockBounds(f, 0.0f, f3, f2, 1.5f, f4);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
        if (connectNegZ) {
            f3 = 0.0f;
        }
        if (connectPosZ) {
            f4 = 1.0f;
        }
        setBlockBounds(f, 0.0f, f3, f2, 1.0f, f4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        boolean connectNegZ = canConnectFenceTo(world, x, y, z - 1);
        boolean connectPosZ = canConnectFenceTo(world, x, y, z + 1);
        boolean connectNegX = canConnectFenceTo(world, x - 1, y, z);
        boolean connectPosX = canConnectFenceTo(world, x + 1, y, z);
        float f = 0.25f;
        float f2 = 0.75f;
        float f3 = 0.25f;
        float f4 = 0.75f;
        if (connectNegZ) {
            f3 = 0.0f;
        }
        if (connectPosZ) {
            f4 = 1.0f;
        }
        if (connectNegX) {
            f = 0.0f;
        }
        if (connectPosX) {
            f2 = 1.0f;
        }
        setBlockBounds(f, 0.0f, f3, f2, 1.0f, f4);
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
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public int getRenderType() {
        return ModuleBlocks.hedgeRenderID;
    }

    @Override
    public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return block == this
                || IBlockFence.isFence(block)
                || block.isLeaves(world, x, y, z)
                || (block.getMaterial().isOpaque()
                        && block.renderAsNormalBlock()
                        && block.getMaterial() != Material.gourd);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        // ignored
    }

    private LeafType getType(int meta) {
        return LeafType.values()[meta % 8];
    }

    private boolean isFull(int meta) {
        return meta / 8 > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        LeafType type = getType(meta);
        return ForestryAPI.textureManager.getIcon(isFull(meta) ? type.plainUID : type.fancyUID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 6; ++i) {
            for (int f = 0; f < 2; ++f) {
                list.add(new ItemStack(item, 1, i + f * 8));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return getColor(world.getBlockMetadata(x, y, z));
    }
}
