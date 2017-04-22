package binnie.core.block;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;

import java.util.*;

public interface IBlockMetadata extends ITileEntityProvider {
	int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction);

	int getDroppedMeta(int blockMeta, int tileMeta);

	String getBlockName(ItemStack itemStack);

	void getBlockTooltip(ItemStack itemStack, List tooltip);

	void dropAsStack(World world, int x, int y, int z, ItemStack itemStack);
}
