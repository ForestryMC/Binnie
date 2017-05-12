package binnie.extrabees.utils.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 12-5-2017.
 */
public class ConfigurationMain implements ConfigHandler.IConfigurable {

	public static boolean canQuarryMineHives = true;

	public static int waterHiveRate = 1;

	public static int rockHiveRate = 2;

	public static int netherHiveRate = 2;

	public static int marbleHiveRate = 2;

	@Override
	public void configure(Configuration config) {
		canQuarryMineHives = config.getBoolean("canQuarryMineHives", Configuration.CATEGORY_GENERAL, true, "Sets whether a quarry will be able to mine hives or not.");

		config.addCustomCategoryComment(WORLDGEN, "WorldGen settings for ExtraBees");
		waterHiveRate = config.getInt("waterHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for water hives.");
		rockHiveRate = config.getInt("rockHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for rock hives.");
		netherHiveRate = config.getInt("netherHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for nether hives.");
		marbleHiveRate = config.getInt("marbleHiveRate", WORLDGEN, 2, 0, 10, "Sets the worldgen spawn chance for marble hives.");
	}

	private static final String WORLDGEN = "World-Gen";

}
