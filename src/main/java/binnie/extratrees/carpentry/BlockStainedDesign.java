package binnie.extratrees.carpentry;

import binnie.botany.Botany;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockStainedDesign extends BlockDesign {
    public BlockStainedDesign() {
        super(DesignSystem.Glass, Material.glass);
        setCreativeTab(Tabs.tabArboriculture);
        setBlockName("stainedGlass");
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
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
        return block != this && block != Botany.stained && super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public ItemStack getCreativeStack(IDesign design) {
        return ModuleCarpentry.getItemStack(this, GlassType.get(0), GlassType.get(1), design);
    }

    @Override
    public String getBlockName(DesignBlock design) {
        return I18N.localise(
                "extratrees.block.stainedglass.name", design.getDesign().getName());
    }

    @Override
    public void addBlockTooltip(ItemStack stack, List tooltip) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
            tooltip.add(I18N.localise(
                    "extratrees.block.tooltip.twoColors",
                    block.getPrimaryMaterial().getName(),
                    block.getSecondaryMaterial().getName()));
        } else {
            tooltip.add(block.getPrimaryMaterial().getName());
        }
    }
}
