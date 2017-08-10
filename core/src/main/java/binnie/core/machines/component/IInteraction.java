package binnie.core.machines.component;

import net.minecraft.entity.player.EntityPlayer;
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
		void onRightClick(final World p0, final EntityPlayer p1, final BlockPos pos);
	}
}
