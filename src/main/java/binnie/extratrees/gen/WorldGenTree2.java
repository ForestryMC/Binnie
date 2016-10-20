package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenTree2 {
    public static class Olive extends WorldGenTree {
        public Olive(final ITreeGenData tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = this.randBetween(2, 3);
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
            this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.5f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Sweetgum extends WorldGenTree {
        public Sweetgum(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = this.randBetween(1, 2);
            float width = this.height * this.randBetween(0.7f, 0.75f);
            if (width > 7.0f) {
                width = 7.0f;
            }
            final float coneHeight = leafSpawn - bottom;
            while (leafSpawn > bottom) {
                float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
                radius *= 2.0f - radius;
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
            this.height = this.determineHeight(5, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Locust extends WorldGenTree {
        public Locust(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 1;
            final float bottom = 2.0f;
            float width = this.height * this.randBetween(0.35f, 0.4f);
            if (width < 2.0f) {
                width = 2.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
            while (leafSpawn > bottom + 1.0f) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.3f, 1, this.leaf, false);
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.7f, 1, this.leaf, false);
            }
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width, 1, this.leaf, false);
            final float f5 = 0.0f;
            final float h5 = leafSpawn;
            leafSpawn = h5 - 1.0f;
            this.generateCylinder(new Vector(f5, h5, 0.0f), width - 0.4f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Pear extends WorldGenTree {
        public Pear(final ITree tree) {
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
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(3, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Iroko extends WorldGenTree {
        public Iroko(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = this.randBetween(2, 3);
            float width = this.height * this.randBetween(0.45f, 0.5f);
            if (width < 2.5f) {
                width = 2.5f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width * 0.25f, 1, this.leaf, false);
            final float f2 = 0.0f;
            final float h2 = leafSpawn;
            leafSpawn = h2 - 1.0f;
            this.generateCylinder(new Vector(f2, h2, 0.0f), width * 0.5f, 1, this.leaf, false);
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), width * 0.7f, 1, this.leaf, false);
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width, 1, this.leaf, false);
            final float f5 = 0.0f;
            final float h5 = leafSpawn;
            leafSpawn = h5 - 1.0f;
            this.generateCylinder(new Vector(f5, h5, 0.0f), width - 2.0f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Gingko extends WorldGenTree {
        public Gingko(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height + 2;
            final float bottom = 2.0f;
            float width = this.height * this.randBetween(0.55f, 0.6f);
            if (width > 7.0f) {
                width = 7.0f;
            }
            final float f = 0.0f;
            final float h = leafSpawn;
            leafSpawn = h - 1.0f;
            this.generateCylinder(new Vector(f, h, 0.0f), width - 2.0f, 1, this.leaf, false);
            while (leafSpawn > bottom * 2.0f + 1.0f) {
                final float f2 = 0.0f;
                final float h2 = leafSpawn;
                leafSpawn = h2 - 1.0f;
                this.generateCylinder(new Vector(f2, h2, 0.0f), width - 1.5f, 1, this.leaf, false);
                final float f3 = 0.0f;
                final float h3 = leafSpawn;
                leafSpawn = h3 - 1.0f;
                this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.9f, 1, this.leaf, false);
            }
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width, 1, this.leaf, false);
            while (leafSpawn > bottom) {
                final float f5 = 0.0f;
                final float h5 = leafSpawn;
                leafSpawn = h5 - 1.0f;
                this.generateCylinder(new Vector(f5, h5, 0.0f), width - 0.9f, 1, this.leaf, false);
                final float f6 = 0.0f;
                final float h6 = leafSpawn;
                leafSpawn = h6 - 1.0f;
                this.generateCylinder(new Vector(f6, h6, 0.0f), width, 1, this.leaf, false);
            }
            final float f7 = 0.0f;
            final float h7 = leafSpawn;
            leafSpawn = h7 - 1.0f;
            this.generateCylinder(new Vector(f7, h7, 0.0f), 0.7f * width, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Box extends WorldGenTree {
        public Box(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = 0.0f;
            final float width = 1.5f;
            while (leafSpawn > bottom) {
                final float f = 0.0f;
                final float h = leafSpawn;
                leafSpawn = h - 1.0f;
                this.generateCylinder(new Vector(f, h, 0.0f), width, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(3, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Clove extends WorldGenTree {
        public Clove(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = this.height;
            final float bottom = 2.0f;
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
                this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.6f, 1, this.leaf, false);
            }
            final float f3 = 0.0f;
            final float h3 = leafSpawn;
            leafSpawn = h3 - 1.0f;
            this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
            final float f4 = 0.0f;
            final float h4 = leafSpawn;
            leafSpawn = h4 - 1.0f;
            this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.4f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }
}
