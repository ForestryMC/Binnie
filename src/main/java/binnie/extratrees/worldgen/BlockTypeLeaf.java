package binnie.extratrees.worldgen;

import binnie.core.Mods;
import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTypeLeaf extends BlockType {
    public BlockTypeLeaf() {
        super(Mods.Forestry.block("leaves"), 0);
    }

    @Override
    public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {
        tree.setLeaves(world, null, pos);
    }
}
