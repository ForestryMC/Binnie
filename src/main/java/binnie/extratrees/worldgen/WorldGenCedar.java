package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenCedar extends WorldGenTree {
    public WorldGenCedar(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 3;
        float bottom = randBetween(2, 3);
        float width = height * randBetween(0.7f, 0.75f);
        if (width > 7.0f) {
            width = 7.0f;
        }

        float coneHeight = leafSpawn - bottom;
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), girth, 1, leaf, false);
        }

        for (leafSpawn = height + 3; leafSpawn > bottom; leafSpawn += 1 + rand.nextInt(2)) {
            float cone = 1.0f - (leafSpawn - bottom) / coneHeight;
            cone *= 2.0f - cone;

            float radius = (0.7f + rand.nextFloat() * 0.3f) * width;
            radius *= cone;
            if (radius < 2.0f) {
                radius = 2.0f;
            }

            float xOffset = (-width + rand.nextFloat() * 2.0f * width) / 2.0f;
            xOffset *= cone;
            if (xOffset > radius / 2.0f) {
                xOffset = radius / 2.0f;
            }

            float yOffset = (-width + rand.nextFloat() * 2.0f * width) / 2.0f;
            yOffset *= cone;
            if (yOffset > radius / 2.0f) {
                yOffset = radius / 2.0f;
            }

            generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.7f * radius, 1, leaf, false);
            generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), radius, 1, leaf, false);
            generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.5f * radius, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
