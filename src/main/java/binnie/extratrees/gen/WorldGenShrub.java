package binnie.extratrees.gen;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

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
			float leafSpawn = this.height + 1;
			float width = this.height * WorldGenUtils.randBetween(rand, 0.7f, 0.9f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.15f * width + girth, 1, EnumReplaceMode.SOFT);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.75f * width + girth, 1, EnumReplaceMode.SOFT);
			while (leafSpawn >= 0.0f) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, width + girth, 1, EnumReplaceMode.SOFT);
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
			return height < minHeight ? minHeight : height > maxHeight ? maxHeight : height;
		}
	}
}
