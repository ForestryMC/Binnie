// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenPalm
{
	public static class Coconut extends WorldGenTree
	{
		public Coconut(final ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.35f, 0.4f);
			if (width < 1.2) {
				width = 1.55f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.6f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
		}
	}
}
