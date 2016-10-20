package binnie.core.machines.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInteraction {
    public interface ChunkUnload {
        void onChunkUnload();
    }

    public interface Invalidation {
        void onInvalidation();
    }

    public interface RightClick {
        void onRightClick(final World p0, final EntityPlayer p1, final BlockPos pos);
    }
}
