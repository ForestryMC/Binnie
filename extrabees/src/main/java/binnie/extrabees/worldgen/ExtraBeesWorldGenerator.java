package binnie.extrabees.worldgen;

import binnie.extrabees.utils.config.ConfigurationMain;

import java.util.LinkedList;

import forestry.apiculture.ModuleApiculture;

public class ExtraBeesWorldGenerator {
	private final LinkedList<WorldGenHive> worldGenHives = new LinkedList<>();

	public void registerHiveWorldGen(WorldGenHive hive) {
		worldGenHives.add(hive);
	}

	public void doInit() {
		ModuleApiculture.getHiveRegistry().registerHive("aqua", BinnieHiveDescription.WATER);
		ModuleApiculture.getHiveRegistry().registerHive("marbla", BinnieHiveDescription.MARBLE);
		ModuleApiculture.getHiveRegistry().registerHive("saxum", BinnieHiveDescription.ROCK);
		ModuleApiculture.getHiveRegistry().registerHive("basalt", BinnieHiveDescription.NETHER);

	}
}
