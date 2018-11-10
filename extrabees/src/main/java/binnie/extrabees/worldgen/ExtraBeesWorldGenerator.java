package binnie.extrabees.worldgen;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import net.minecraftforge.fml.common.IWorldGenerator;

import binnie.extrabees.utils.config.ConfigurationMain;

public class ExtraBeesWorldGenerator implements IWorldGenerator {
	private final LinkedList<WorldGenHive> worldGenHives = new LinkedList<>();

	public void registerHiveWorldGen(WorldGenHive hive) {
		worldGenHives.add(hive);
	}

	public void doInit() {
		registerHiveWorldGen(new WorldGenHiveWater(ConfigurationMain.getWaterHiveRate()));
		registerHiveWorldGen(new WorldGenHiveRock(ConfigurationMain.getRockHiveRate()));
		registerHiveWorldGen(new WorldGenHiveNether(ConfigurationMain.getNetherHiveRate()));
		registerHiveWorldGen(new WorldGenHiveMarble(ConfigurationMain.getMarbleHiveRate()));
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		worldGenHives.forEach(hive -> generateHive(hive, rand, chunkX, chunkZ, world));
	}

	private void generateHive(WorldGenHive hive, Random rand, int chunkX, int chunkZ, World world) {
		chunkX <<= 4;
		chunkZ <<= 4;
		for (int i = 0; i < hive.getRate(); ++i) {
			final int randPosX = chunkX + rand.nextInt(16) + 8;
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16) + 8;
			hive.generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}
}
