package binnie.extrabees.worldgen;


import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenHive extends WorldGenerator {
	private int rate;

	public WorldGenHive(int rate) {
		this.rate = rate;
	}

	public int getRate() {
		return rate;
	}
}
