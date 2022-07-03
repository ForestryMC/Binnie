package binnie.extratrees.worldgen;

import binnie.extratrees.block.ILogType;
import forestry.api.world.ITreeGenData;
import net.minecraft.world.World;

public class BlockTypeLog extends BlockType {
    protected ILogType log;

    public BlockTypeLog(ILogType log) {
        super(null, 0);
        this.log = log;
    }

    @Override
    public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
        log.placeBlock(world, x, y, z);
    }
}
