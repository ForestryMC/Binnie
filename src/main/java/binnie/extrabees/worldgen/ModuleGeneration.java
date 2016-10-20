// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import java.util.Random;
import binnie.core.genetics.ForestryAllele;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.apiculture.IAlleleBeeSpecies;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import buildcraft.api.core.BuildCraftAPI;
import binnie.extrabees.config.ConfigurationMain;
import cpw.mods.fml.common.registry.GameRegistry;
import binnie.extrabees.ExtraBees;
import binnie.core.IInitializable;
import cpw.mods.fml.common.IWorldGenerator;

public class ModuleGeneration implements IWorldGenerator, IInitializable
{
	static int waterRate;
	static int rockRate;
	static int netherRate;
	static int marbleRate;

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
	public void generate(final Random rand, int chunkX, int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		chunkX <<= 4;
		chunkZ <<= 4;
		for (int i = 0; i < ModuleGeneration.waterRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveWater().generate(world, rand, randPosX, randPosY, randPosZ);
		}
		for (int i = 0; i < ModuleGeneration.rockRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveRock().generate(world, rand, randPosX, randPosY, randPosZ);
		}
		for (int i = 0; i < ModuleGeneration.netherRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveNether().generate(world, rand, randPosX, randPosY, randPosZ);
		}
	}

	static {
		ModuleGeneration.waterRate = 2;
		ModuleGeneration.rockRate = 2;
		ModuleGeneration.netherRate = 2;
		ModuleGeneration.marbleRate = 2;
	}
}
