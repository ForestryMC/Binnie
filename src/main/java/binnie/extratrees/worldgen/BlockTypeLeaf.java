package binnie.extratrees.worldgen;

import binnie.core.Mods;
import binnie.extratrees.ExtraTrees;
import forestry.api.world.ITreeGenData;
import net.minecraft.world.World;

public class BlockTypeLeaf extends BlockType {
    public BlockTypeLeaf(boolean decay) {
        super(decay ? Mods.forestry.block("leaves") : ExtraTrees.blockShrubLeaves, 0);
    }

    @Override
    public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
        tree.setLeaves(world, null, x, y, z);
    }
}
