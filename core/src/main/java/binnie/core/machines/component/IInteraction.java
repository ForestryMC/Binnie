package binnie.core.machines.component;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInteraction {
	interface ChunkUnload {
		void onChunkUnload();
	}

	interface Invalidation {
		void onInvalidation();
	}

	interface RightClick {
		void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
	}
}
