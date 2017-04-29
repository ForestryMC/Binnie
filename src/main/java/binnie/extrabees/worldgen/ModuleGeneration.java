package binnie.extrabees.worldgen;

import binnie.core.IInitializable;
import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import buildcraft.api.core.BuildCraftAPI;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class ModuleGeneration implements IWorldGenerator, IInitializable {
	protected static int waterRate = 2;
	protected static int rockRate = 2;
	protected static int netherRate = 2;
	protected static int marbleRate = 2;

	@Override
	public void preInit() {
		ExtraBees.materialBeehive = new MaterialBeehive();
		GameRegistry.registerBlock(ExtraBees.hive = new BlockExtraBeeHive(), ItemBeehive.class, "hive");
	}

	@Override
	public void init() {
		ModuleGeneration.waterRate = ConfigurationMain.waterHiveRate;
		ModuleGeneration.rockRate = ConfigurationMain.rockHiveRate;
		ModuleGeneration.netherRate = ConfigurationMain.netherHiveRate;
		GameRegistry.registerWorldGenerator(new ModuleGeneration(), 0);
		if (!ConfigurationMain.canQuarryMineHives) {
			BuildCraftAPI.softBlocks.add(ExtraBees.hive);
		}
	}

	@Override
	public void postInit() {
		EnumHiveType.Water.drops.add(new HiveDrop(ExtraBeesSpecies.WATER, 80));
		EnumHiveType.Water.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));

		EnumHiveType.Rock.drops.add(new HiveDrop(ExtraBeesSpecies.ROCK, 80));
		EnumHiveType.Rock.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));

		EnumHiveType.Nether.drops.add(new HiveDrop(ExtraBeesSpecies.BASALT, 80));
		EnumHiveType.Nether.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));

		ExtraBees.hive.setHarvestLevel("scoop", 0, 0);
		ExtraBees.hive.setHarvestLevel("scoop", 0, 1);
		ExtraBees.hive.setHarvestLevel("scoop", 0, 2);
		ExtraBees.hive.setHarvestLevel("scoop", 0, 3);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		chunkX <<= 4;
		chunkZ <<= 4;

		for (int i = 0; i < ModuleGeneration.waterRate; ++i) {
			int randPosX = chunkX + rand.nextInt(16);
			int randPosY = rand.nextInt(50) + 20;
			int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveWater().generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int i = 0; i < ModuleGeneration.rockRate; ++i) {
			int randPosX = chunkX + rand.nextInt(16);
			int randPosY = rand.nextInt(50) + 20;
			int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveRock().generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int i = 0; i < ModuleGeneration.netherRate; ++i) {
			int randPosX = chunkX + rand.nextInt(16);
			int randPosY = rand.nextInt(50) + 20;
			int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveNether().generate(world, rand, randPosX, randPosY, randPosZ);
		}
	}
}
