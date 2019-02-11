package binnie.extrabees.utils.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationMain implements IConfigurable {

	private static final String WORLDGEN = "World-Gen";
	private static boolean canQuarryMineHives = true;
	private static float waterHiveRate = 2.0F;
	private static float rockHiveRate = 2.0F;
	private static float netherHiveRate = 2.0F;
	private static float marbleHiveRate = 2.0F;

	public static boolean isCanQuarryMineHives() {
		return canQuarryMineHives;
	}

	public static float getWaterHiveRate() {
		return waterHiveRate;
	}

	public static float getRockHiveRate() {
		return rockHiveRate;
	}

	public static float getNetherHiveRate() {
		return netherHiveRate;
	}

	public static float getMarbleHiveRate() {
		return marbleHiveRate;
	}

	@Override
	public void configure(Configuration config) {
		canQuarryMineHives = config.getBoolean("canQuarryMineHives", Configuration.CATEGORY_GENERAL, true, "Sets whether a quarry will be able to mine hives or not.");

		config.addCustomCategoryComment(WORLDGEN, "WorldGen settings for ExtraBees");
		waterHiveRate = config.getFloat("waterHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for water hives.");
		rockHiveRate = config.getFloat("rockHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for rock hives.");
		netherHiveRate = config.getFloat("netherHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for nether hives.");
		marbleHiveRate = config.getFloat("marbleHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for marble hives.");
	}
}
