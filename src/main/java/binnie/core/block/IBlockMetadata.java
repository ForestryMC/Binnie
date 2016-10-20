package binnie.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IBlockMetadata extends ITileEntityProvider {
    int getPlacedMeta(final ItemStack p0, final World p1, BlockPos pos, final EnumFacing p5);

    int getDroppedMeta(final int p0, final int p1);

    String getBlockName(final ItemStack p0);

    void getBlockTooltip(final ItemStack p0, final List p1);

    void dropAsStack(final World p0, BlockPos pos, final ItemStack p4);
}
