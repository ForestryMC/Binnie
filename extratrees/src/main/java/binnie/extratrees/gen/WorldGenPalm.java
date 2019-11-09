package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenPalm {
	public static class Coconut extends WorldGenTree {
		public Coconut(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.6f, 1, this.leaf, false);		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(8, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
