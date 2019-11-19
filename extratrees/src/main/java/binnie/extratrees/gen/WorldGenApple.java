package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldGenApple {
	public static class OrchardApple extends BinnieWorldGenTree {
		public OrchardApple(ITreeGenData tree) {
			super(tree, 3, 6);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height;

			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.5f, 1, this.leaf, false);
			while (leafSpawn > 2) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn,0), girth + 1, 1, this.leaf, false);
		}
	}

	public static class SweetCrabapple extends BinnieWorldGenTree {
		public SweetCrabapple(ITreeGenData tree) {
			super(tree, 4, 4);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
			while(leafSpawn > 3) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn,0), girth + 0.5f, 1, this.leaf, false);
		}
	}

	public static class FloweringCrabapple extends BinnieWorldGenTree {
		public FloweringCrabapple(ITreeGenData tree) {
			super(tree, 3, 6);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2, 1, this.leaf, false);
			while(leafSpawn > 3) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 3, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn,0), girth + 1, 1, this.leaf, false);
		}
	}

	public static class PrairieCrabapple extends BinnieWorldGenTree {
		public PrairieCrabapple(ITreeGenData tree) {
			super(tree, 4, 4);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
			while(leafSpawn > 3) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.85f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.85f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
		}
	}
}
