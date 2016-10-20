package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenWalnut {
    public static class BlackWalnut extends WorldGenTree {
        public BlackWalnut(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height + 1;
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, this.leaf, false);
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.6f, 1, this.leaf, false);
            while (leafSpawn > this.randBetween(3, 4)) {
                this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.8f, 1, this.leaf, false);
            }
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.7f, 1, this.leaf, false);
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.8f, 1, this.leaf, false);
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(9, 6);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }

    public static class Butternut extends WorldGenTree {
        public Butternut(final ITree tree) {
            super(tree);
        }

        @Override
        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height + 1;
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, this.leaf, false);
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.5f, 1, this.leaf, false);
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.5f, 1, this.leaf, false);
            this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, this.leaf, false);
            while (leafSpawn > 3) {
                this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, this.leaf, false);
            }
            if (this.rand.nextBoolean()) {
                this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.0f, 1, this.leaf, false);
            }
        }

        @Override
        public void preGenerate() {
            this.height = this.determineHeight(6, 3);
            this.girth = this.determineGirth(this.treeGen.getGirth());
        }
    }
}
