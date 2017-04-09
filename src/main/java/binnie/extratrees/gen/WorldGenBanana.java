package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.ITreeBlockType;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;
import forestry.core.worldgen.WorldGenHelper.EnumReplaceMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WorldGenBanana extends forestry.arboriculture.worldgen.WorldGenTree {
	public WorldGenBanana(ITreeGenData tree) {
		super(tree, 6, 1);
	}
	
	@Override
	protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
		int leafSpawn = this.height + 1;
		final float width = this.height / WorldGenUtils.randBetween(rand, 3f, 3.25f);
		WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.25F * width + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, width + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.75F * width + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
	}

}
