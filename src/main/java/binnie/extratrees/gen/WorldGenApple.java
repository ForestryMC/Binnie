// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenApple
{
	public static class OrchardApple extends WorldGenTree
	{
		public OrchardApple(final ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
			while (leafSpawn > 2) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(3, 6);
			this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
		}
	}

	public static class SweetCrabapple extends WorldGenTree
	{
		public SweetCrabapple(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, this.leaf, false);
			while (leafSpawn > 3) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 4);
			this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
		}
	}

	public static class FloweringCrabapple extends WorldGenTree
	{
		public FloweringCrabapple(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, this.leaf, false);
			while (leafSpawn > 3) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.0f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(3, 6);
			this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
		}
	}

	public static class PrairieCrabapple extends WorldGenTree
	{
		public PrairieCrabapple(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, this.leaf, false);
			while (leafSpawn > 3) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 4);
			this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
		}
	}
}
