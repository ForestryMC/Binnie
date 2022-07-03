package binnie.core.block;

import java.util.List;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockMetadata extends ITileEntityProvider {
    int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction);

    int getDroppedMeta(int blockMeta, int tileMeta);

    String getBlockName(ItemStack itemStack);

    void addBlockTooltip(ItemStack itemStack, List tooltip);

    void dropAsStack(World world, int x, int y, int z, ItemStack itemStack);
}
