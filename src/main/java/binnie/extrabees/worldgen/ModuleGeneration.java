package binnie.extrabees.worldgen;

import binnie.core.IInitializable;
import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class ModuleGeneration implements IWorldGenerator, IInitializable {
    static int waterRate = 2;
    static int rockRate = 2;
    static int netherRate = 2;
    static int marbleRate = 2;

    @Override
    public void preInit() {
        ExtraBees.materialBeehive = new MaterialBeehive();
        GameRegistry.register(ExtraBees.hive = new BlockExtraBeeHive());
        GameRegistry.register(new ItemBeehive(ExtraBees.hive).setRegistryName("hive"));
    }

    @Override
    public void init() {
        ModuleGeneration.waterRate = ConfigurationMain.waterHiveRate;
        ModuleGeneration.rockRate = ConfigurationMain.rockHiveRate;
        ModuleGeneration.netherRate = ConfigurationMain.netherHiveRate;
        GameRegistry.registerWorldGenerator(new ModuleGeneration(), 0);
        if (!ConfigurationMain.canQuarryMineHives) {
            //BuildCraftAPI.softBlocks.add(ExtraBees.hive);
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
        ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Water));
        ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Rock));
        ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Nether));
        ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Marble));
    }

    @Override
    public void generate(final Random rand, int chunkX, int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        chunkX <<= 4;
        chunkZ <<= 4;
        for (int i = 0; i < ModuleGeneration.waterRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveWater().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
        }
        for (int i = 0; i < ModuleGeneration.rockRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveRock().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
        }
        for (int i = 0; i < ModuleGeneration.netherRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveNether().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
        }
    }

}
