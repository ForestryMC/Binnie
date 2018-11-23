package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenEucalyptus {
	public static class SwampGum extends forestry.arboriculture.worldgen.WorldGenTree {
		public SwampGum(ITreeGenData tree) {
			super(tree, 14, 3);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = height + 2;
			int weakerBottom = (int) (height * (0.5 + rand.nextFloat() * 0.3F));
			int bottom = (int) (height * (0.45 + rand.nextFloat() * 0.2F));
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 0.75F, 1, WorldGenHelper.EnumReplaceMode.SOFT);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1.75F, 1, WorldGenHelper.EnumReplaceMode.SOFT);
			while (leafSpawn > weakerBottom) {
				WorldGenUtils.generateCylinderFromTreeStartPos(world, rand, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1F + (1.75F * rand.nextFloat()), 1, 0.3F, WorldGenHelper.EnumReplaceMode.SOFT);
			}
			while (leafSpawn > bottom) {
				WorldGenUtils.generateCylinderFromTreeStartPos(world, rand, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 0.75F + (1.25F * rand.nextFloat()), 1, 0.3F, WorldGenHelper.EnumReplaceMode.SOFT);
			}
			for (int i = 0; i < 7; ++i) {
				if (rand.nextFloat() > 0.45) {
					leafSpawn--;
					continue;
				}
				WorldGenHelper.generateSphereFromTreeStartPos(world, startPos.add(rand.nextInt(girth) * (rand.nextBoolean() ? -1 : 1), leafSpawn--, rand.nextInt(girth) * (rand.nextBoolean() ? -1 : 1)), girth, 1, leaf, WorldGenHelper.EnumReplaceMode.SOFT);
			}
		}

	}

	public static class RoseGum extends WorldGenTree {
		public RoseGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int offset = (this.girth - 1) / 2;
			for (int x = 0; x < this.girth; ++x) {
				for (int y = 0; y < this.girth; ++y) {
					for (int i = 0; i < 2; ++i) {
						//TODO:
						//this.addBlock(x - offset, i, y - offset, new BlockTypeLog(EnumETLog.Eucalyptus2), true);
					}
				}
			}
			float leafSpawn = this.height + 2;
			float bottom = this.randBetween(0.4f, 0.5f) * this.height;
			float width = this.height * this.randBetween(0.05f, 0.1f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.bushiness = 0.5f;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.bushiness = 0.1f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(9, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class RainbowGum extends WorldGenTree {
		public RainbowGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float bottom = this.randBetween(0.5f, 0.6f) * this.height;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
