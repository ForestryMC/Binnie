package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenSpruce {
    public static class GiantSpruce extends WorldGenTree {
        public GiantSpruce(final ITreeGenData tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 3;
            final float bottom = this.randBetween(3, 4);
            final float width = this.height / this.randBetween(2.5f, 3.0f);
            final float coneHeight = leafSpawn - bottom;
            while (leafSpawn > bottom) {
                float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
                radius = 0.15f + 0.85f * radius;
                radius *= width;
                final float f = 0.0f;
                final float h = leafSpawn;
                leafSpawn = h - 1.0f;
                this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
            }
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(15, 4);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class AlpineSpruce extends WorldGenTree {
        public AlpineSpruce(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 5;
            final float bottom = this.randBetween(2, 3);
            final float width = this.height / this.randBetween(2.0f, 2.5f);
            final float coneHeight = leafSpawn - bottom;
            leafSpawn -= 2.0f;
            while (leafSpawn > bottom) {
                float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
                radius *= radius;
                radius *= width;
                final float f = 0.0f;
                final float h = leafSpawn;
                leafSpawn = h - 1.0f;
                this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
            }
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(5, 3);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class WhiteSpruce extends WorldGenTree {
        public WhiteSpruce(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 2;
            final float bottom = this.randBetween(2, 3);
            final float width = this.height / this.randBetween(2.2f, 2.5f);
            final float coneHeight = leafSpawn - bottom;
            while (leafSpawn > bottom) {
                float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
                radius = (float) Math.sqrt(radius);
                radius *= width;
                final float f = 0.0f;
                final float h = leafSpawn;
                leafSpawn = h - 1.0f;
                this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
            }
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class BlackSpruce extends WorldGenTree {
        public BlackSpruce(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 2;
            final float bottom = this.randBetween(2, 3);
            final float width = this.height / this.randBetween(2.2f, 2.5f);
            final float coneHeight = leafSpawn - bottom;
            while (leafSpawn > bottom) {
                float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
                radius = (float) Math.sqrt(radius);
                radius *= width;
                final float f = 0.0f;
                final float h = leafSpawn;
                leafSpawn = h - 1.0f;
                this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
            }
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }
}
