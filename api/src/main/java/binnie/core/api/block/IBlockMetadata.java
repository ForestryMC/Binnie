package binnie.core.api.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TODO: move out of API
public interface IBlockMetadata extends ITileEntityProvider {
	int getPlacedMeta(ItemStack itemStack, World world, BlockPos pos, EnumFacing facing);

	int getDroppedMeta(IBlockState state, int tileMetadata);

	String getDisplayName(ItemStack itemStack);
}
