package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenConifer {
	public static class WesternHemlock extends WorldGenTree {
		public WesternHemlock(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 6;
			float bottom = randBetween(2, 3);
			float width = height / randBetween(2.0f, 2.5f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Cypress extends WorldGenTree {
		public Cypress(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = 1.0f;
			float width = height * randBetween(0.15f, 0.2f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width - 1.0f;
				++radius;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Yew extends WorldGenTree {
		public Yew(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(1, 2);
			float width = height * randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= 2.0f - radius;
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Cedar extends WorldGenTree {
		public Cedar(ITree tree) {
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
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), girth, 1, leaf, false);
			}
			for (leafSpawn = height + 3; leafSpawn > bottom; leafSpawn += 1 + rand.nextInt(2)) {
				float cone = 1.0f - (leafSpawn - bottom) / coneHeight;
				float radius = (0.7f + rand.nextFloat() * 0.3f) * width;
				float xOffset = (-width + rand.nextFloat() * 2.0f * width) / 2.0f;
				float yOffset = (-width + rand.nextFloat() * 2.0f * width) / 2.0f;
				cone *= 2.0f - cone;
				xOffset *= cone;
				yOffset *= cone;
				radius *= cone;
				if (radius < 2.0f) {
					radius = 2.0f;
				}
				if (xOffset > radius / 2.0f) {
					xOffset = radius / 2.0f;
				}
				if (yOffset > radius / 2.0f) {
					yOffset = radius / 2.0f;
				}
				float f2 = xOffset;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, yOffset), 0.7f * radius, 1, leaf, false);
				float f3 = xOffset;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, yOffset), radius, 1, leaf, false);
				float f4 = xOffset;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, yOffset), 0.5f * radius, 1, leaf, false);
			}
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class LoblollyPine extends WorldGenTree {
		public LoblollyPine(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = height * randBetween(0.65f, 0.7f);
			float width = height * randBetween(0.25f, 0.3f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.6f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			}
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class MonkeyPuzzle extends WorldGenTree {
		public MonkeyPuzzle(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.4f, 0.45f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.35f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.55f * width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.75f * width, 1, leaf, false);
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), 0.9f * width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 1.0f * width, 1, leaf, false);
			float f6 = 0.0f;
			float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			generateCylinder(new Vector(f6, h6, 0.0f), 0.5f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(9, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
