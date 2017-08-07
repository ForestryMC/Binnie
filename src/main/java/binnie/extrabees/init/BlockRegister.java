package binnie.extrabees.init;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.apiculture.genetics.BeeDefinition;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.TileEntityExtraBeesAlvearyPart;
import binnie.extrabees.blocks.BlockEctoplasm;
import binnie.extrabees.blocks.BlockExtraBeeHive;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.extrabees.utils.HiveDrop;
import binnie.extrabees.utils.Utils;
import binnie.extrabees.utils.config.ConfigurationMain;

public final class BlockRegister {

	public static void preInitBlocks() {
		registerHives();
		registerMisc();
		ExtraBees.alveary = ExtraBees.proxy.registerBlock(new BlockAlveary());
		GameRegistry.registerTileEntity(TileEntityExtraBeesAlvearyPart.class, "TileEntityExtraBeesAlvearyPart");
	}

	public static void postInitBlocks() {
		registerHiveDrops();
	}

	private static void registerMisc() {
		ForgeRegistries.BLOCKS.register(ExtraBees.ectoplasm = new BlockEctoplasm());
	}

	@SuppressWarnings("all")
	private static void registerHives() {
		ForgeRegistries.BLOCKS.register(ExtraBees.hive = new BlockExtraBeeHive());
	}

	private static void registerHiveDrops() {
		IAlleleBeeSpecies valiantSpecies = Utils.getSpecies(BeeDefinition.VALIANT);
		EnumHiveType.Water.drops.add(new HiveDrop(ExtraBeeDefinition.WATER, 80));
		EnumHiveType.Water.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Rock.drops.add(new HiveDrop(ExtraBeeDefinition.ROCK, 80));
		EnumHiveType.Rock.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Nether.drops.add(new HiveDrop(ExtraBeeDefinition.BASALT, 80));
		EnumHiveType.Nether.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Marble.drops.add(new HiveDrop(ExtraBeeDefinition.MARBLE, 80));
		EnumHiveType.Marble.drops.add(new HiveDrop(valiantSpecies, 3));

		ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Water));
		ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Rock));
		ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Nether));
		ExtraBees.hive.setHarvestLevel("scoop", 0, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Marble));
		if (!ConfigurationMain.canQuarryMineHives) {
			//BuildCraftAPI.softBlocks.add(ExtraBees.hive);
		}
	}
}
