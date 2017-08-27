package binnie.extrabees.utils.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationMain implements IConfigurable {

	private static final String WORLDGEN = "World-Gen";
	private static boolean canQuarryMineHives = true;
	private static int waterHiveRate = 2;
	private static int rockHiveRate = 2;
	private static int netherHiveRate = 2;
	private static int marbleHiveRate = 2;

	public static boolean isCanQuarryMineHives() {
		return canQuarryMineHives;
	}

	public static int getWaterHiveRate() {
		return waterHiveRate;
	}

	public static int getRockHiveRate() {
		return rockHiveRate;
	}

	public static int getNetherHiveRate() {
		return netherHiveRate;
	}

	public static int getMarbleHiveRate() {
		return marbleHiveRate;
	}

	@Override
	public void configure(Configuration config) {
		canQuarryMineHives = config.getBoolean("canQuarryMineHives", Configuration.CATEGORY_GENERAL, true, "Sets whether a quarry will be able to mine hives or not.");

		config.addCustomCategoryComment(WORLDGEN, "WorldGen settings for ExtraBees");
		waterHiveRate = config.getInt("waterHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for water hives.");
		rockHiveRate = config.getInt("rockHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for rock hives.");
		netherHiveRate = config.getInt("netherHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for nether hives.");
		marbleHiveRate = config.getInt("marbleHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for marble hives.");
	}
}
