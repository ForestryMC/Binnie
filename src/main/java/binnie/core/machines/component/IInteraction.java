package binnie.core.machines.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IInteraction {
	interface ChunkUnload {
		void onChunkUnload();
	}

	interface Invalidation {
		void onInvalidation();
	}

	interface RightClick {
		void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z);
	}
}
