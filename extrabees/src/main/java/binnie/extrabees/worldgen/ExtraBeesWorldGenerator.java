package binnie.extrabees.worldgen;

import binnie.core.util.Log;
import forestry.api.apiculture.hives.HiveManager;

public class ExtraBeesWorldGenerator {

	public void doInit() {
		if (HiveManager.hiveRegistry == null) {
			Log.warning("Can't get hive registry, no hives will be generated in the world");
			return;
		}

		HiveManager.hiveRegistry.registerHive("aqua", BinnieHiveDescription.WATER);
		HiveManager.hiveRegistry.registerHive("marbla", BinnieHiveDescription.MARBLE);
		HiveManager.hiveRegistry.registerHive("saxum", BinnieHiveDescription.ROCK);
		HiveManager.hiveRegistry.registerHive("basalt", BinnieHiveDescription.NETHER);
	}
}
