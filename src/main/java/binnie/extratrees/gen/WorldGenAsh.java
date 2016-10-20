package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenAsh {
    public static class CommonAsh extends WorldGenTree {
        public CommonAsh(final ITreeGenData tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = this.randBetween(2, 3);
            final float width = this.height * this.randBetween(0.35f, 0.45f);
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), 0.8f * width, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
            }
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), 0.7f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }
}
