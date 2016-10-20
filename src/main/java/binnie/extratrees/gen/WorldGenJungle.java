package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenJungle {
    public static class Brazilwood extends WorldGenTree {
        public Brazilwood(final ITreeGenData tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = 1.0f;
            float width = this.height * this.randBetween(0.15f, 0.2f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 0.8f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(4, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class Logwood extends WorldGenTree {
        public Logwood(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = this.randBetween(1, 2);
            float width = this.height * this.randBetween(0.25f, 0.3f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.7f, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(4, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class Rosewood extends WorldGenTree {
        public Rosewood(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = this.randBetween(1, 2);
            float width = this.height * this.randBetween(0.2f, 0.25f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
                final float f4 = 0.0f;
                final float h4 = leafSpawn;
                leafSpawn = h4 - 1.0f;
                this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.7f, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class Purpleheart extends WorldGenTree {
        public Purpleheart(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = this.height - 3;
            float width = this.height * this.randBetween(0.2f, 0.25f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, this.leaf, false);
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.7f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(7, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class OsangeOsange extends WorldGenTree {
        public OsangeOsange(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = this.randBetween(1, 2);
            float width = this.height * this.randBetween(0.2f, 0.25f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.6f, 1, this.leaf, false);
            }
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(5, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class OldFustic extends WorldGenTree {
        public OldFustic(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = this.randBetween(1, 2);
            float width = this.height * this.randBetween(0.25f, 0.3f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 0.7f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.5f, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class Coffee extends WorldGenTree {
        public Coffee(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = 1.0f;
            float width = this.height * this.randBetween(0.25f, 0.3f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 0.5f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
            }
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.3f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(3, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class BrazilNut extends WorldGenTree {
        public BrazilNut(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = this.height - 3;
            float width = this.height * this.randBetween(0.25f, 0.3f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
            }
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(7, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }

    public static class Shrub15 extends WorldGenTree {
        public Shrub15(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = 1.0f;
            float width = this.height * this.randBetween(0.15f, 0.2f);
            if (width < 1.5f) {
                width = 1.5f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 0.8f, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }

    }
}
