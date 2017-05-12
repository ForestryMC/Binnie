package binnie.extrabees.init;

import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHive;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.genetics.effect.BlockEctoplasm;
import binnie.extrabees.items.ItemBeehive;
import binnie.extrabees.utils.HiveDrop;
import binnie.extrabees.utils.config.ConfigurationMain;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class BlockRegister {

	public static void preInitBlocks(){
		registerHives();
		registerMisc();
	}

	public static void postInitBlocks(){
		registerHiveDrops();
	}

	private static void registerMisc(){
		GameRegistry.register(ExtraBees.ectoplasm = new BlockEctoplasm());
	}

	private static void registerHives(){
		GameRegistry.register(ExtraBees.hive = new BlockExtraBeeHive());
		GameRegistry.register(new ItemBeehive(ExtraBees.hive).setRegistryName("hive"));
	}

	private static void registerHiveDrops(){
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
		if (!ConfigurationMain.canQuarryMineHives) {
			//BuildCraftAPI.softBlocks.add(ExtraBees.hive);
		}
	}

}
