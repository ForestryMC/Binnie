package binnie.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public interface IBlockMetadata extends ITileEntityProvider {
	int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction);

	int getDroppedMeta(int blockMeta, int tileMeta);

	String getBlockName(ItemStack itemStack);

	void getBlockTooltip(ItemStack itemStack, List tooltip);

	void dropAsStack(World world, int x, int y, int z, ItemStack itemStack);
}
