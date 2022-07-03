package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockType {
    protected int meta;
    protected Block block;

    public BlockType(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
        world.setBlock(x, y, z, block, meta, 0);
        if (world.getTileEntity(x, y, z) != null) {
            world.removeTileEntity(x, y, z);
        }
    }
}
