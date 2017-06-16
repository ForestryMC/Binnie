package binnie.extrabees.worldgen;

import binnie.extrabees.utils.config.ConfigurationMain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.LinkedList;
import java.util.Random;

public class ExtraBeesWorldGenerator implements IWorldGenerator {
	private LinkedList<WorldGenHive> worldGenHives = new LinkedList<>();

	public void registerHiveWorldGen(WorldGenHive hive) {
		worldGenHives.add(hive);
	}

	public void doInit() {
		registerHiveWorldGen(new WorldGenHiveWater(ConfigurationMain.waterHiveRate));
		registerHiveWorldGen(new WorldGenHiveRock(ConfigurationMain.rockHiveRate));
		registerHiveWorldGen(new WorldGenHiveNether(ConfigurationMain.netherHiveRate));
		registerHiveWorldGen(new WorldGenHiveMarble(ConfigurationMain.marbleHiveRate));
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		worldGenHives.stream().forEach(hive -> generateHive(hive, rand, chunkX, chunkZ, world));
	}

	private void generateHive(WorldGenHive hive, Random rand, int chunkX, int chunkZ, World world) {
		chunkX <<= 4;
		chunkZ <<= 4;
		for (int i = 0; i < hive.getRate(); ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			hive.generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}
}
