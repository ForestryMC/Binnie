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
        void onRightClick(World world, EntityPlayer player, int x, int y, int z);
    }
}
