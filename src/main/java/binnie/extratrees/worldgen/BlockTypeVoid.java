package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTypeVoid extends BlockType {
    public BlockTypeVoid() {
        super(null, 0);
    }

    @Override
    public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {
        world.setBlockToAir(pos);
        if (world.getTileEntity(pos) != null) {
            world.removeTileEntity(pos);
        }
    }
}
