package binnie.extratrees.block;

import forestry.arboriculture.blocks.BlockForestryLeaves;
import net.minecraft.world.World;

public class BlockShrubLeaves extends BlockForestryLeaves {
    public BlockShrubLeaves() {
        super();
    }

    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {
        // ignored, no able decay
    }
}
