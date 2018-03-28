package binnie.extrabees.worldgen;

import forestry.apiculture.ModuleApiculture;

public class ExtraBeesWorldGenerator {

	public void doInit() {
		ModuleApiculture.getHiveRegistry().registerHive("aqua", BinnieHiveDescription.WATER);
		ModuleApiculture.getHiveRegistry().registerHive("marbla", BinnieHiveDescription.MARBLE);
		ModuleApiculture.getHiveRegistry().registerHive("saxum", BinnieHiveDescription.ROCK);
		ModuleApiculture.getHiveRegistry().registerHive("basalt", BinnieHiveDescription.NETHER);

	}
}
