package binnie.extratrees.gen;

import java.util.Random;

public class WorldGenUtils {

	public static int randBetween(Random rand, int a, int b) {
		return a + rand.nextInt(b - a);
	}

	public static float randBetween(Random rand, float a, float b) {
		return a + rand.nextFloat() * (b - a);
	}
}
