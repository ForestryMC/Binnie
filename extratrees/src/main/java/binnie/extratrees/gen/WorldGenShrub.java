package binnie.extratrees.gen;

import forestry.api.arboriculture.ITreeModifier;
import forestry.api.arboriculture.TreeManager;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.arboriculture.worldgen.TreeBlockTypeLog;
import forestry.arboriculture.worldgen.WorldGenArboriculture;
import forestry.core.worldgen.WorldGenHelper;
import forestry.core.worldgen.WorldGenHelper.EnumReplaceMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldGenShrub {
	public static class Shrub extends WorldGenArboriculture {
		private static final int minHeight = 1;
		private static final int maxHeight = 10;

		protected int girth;
		protected int height;

		public Shrub(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public Set<BlockPos> generateTrunk(World world, Random rand, TreeBlockTypeLog wood, BlockPos startPos) {
			WorldGenHelper.generateTreeTrunk(world, rand, wood, startPos, height, girth, 0, 0, null, 0);
			return Collections.emptySet();
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth, 1, EnumReplaceMode.SOFT);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1F, 1, EnumReplaceMode.SOFT);
			int i = 0;
			while (leafSpawn >= 0.0f) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2F + i, 1, EnumReplaceMode.SOFT);
				i++;
			}
		}

		@Override
		protected void generateExtras(World world, Random rand, BlockPos startPos) {
		}

		@Override
		@Nullable
		public BlockPos getValidGrowthPos(World world, BlockPos pos) {
			return tree.canGrow(world, pos, girth, height);
		}

		@Override
		public final void preGenerate(World world, Random rand, BlockPos startPos) {
			super.preGenerate(world, rand, startPos);
			height = determineHeight(world, rand, 1, 1);
			girth = tree.getGirth();
		}

		private int determineHeight(World world, Random rand, int required, int variation) {
			ITreeModifier treeModifier = TreeManager.treeRoot.getTreekeepingMode(world);
			int baseHeight = required + rand.nextInt(variation);
			int height = Math.round(baseHeight * tree.getHeightModifier() * treeModifier.getHeightModifier(tree.getGenome(), 1f));
			return height < minHeight ? minHeight : Math.min(height, maxHeight);
		}
	}
}
