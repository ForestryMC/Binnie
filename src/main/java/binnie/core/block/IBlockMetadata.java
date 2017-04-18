package binnie.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public interface IBlockMetadata extends ITileEntityProvider {
	int getPlacedMeta(final ItemStack itemStack, final World world, final int x, final int y, final int z, final ForgeDirection direction);

	int getDroppedMeta(final int p0, final int p1);

	String getBlockName(final ItemStack itemStack);

	void getBlockTooltip(final ItemStack itemStack, final List p1);

	void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack itemStack);
}
