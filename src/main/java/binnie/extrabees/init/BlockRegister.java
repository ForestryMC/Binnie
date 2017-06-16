package binnie.extrabees.init;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.TileEntityExtraBeesAlvearyPart;
import binnie.extrabees.blocks.BlockEctoplasm;
import binnie.extrabees.blocks.BlockExtraBeeHive;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.utils.HiveDrop;
import binnie.extrabees.utils.Utils;
import binnie.extrabees.utils.config.ConfigurationMain;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.apiculture.genetics.BeeDefinition;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		GameRegistry.register(ExtraBees.ectoplasm = new BlockEctoplasm());
	}

	@SuppressWarnings("all")
	private static void registerHives() {
		GameRegistry.register(ExtraBees.hive = new BlockExtraBeeHive());
	}

	private static void registerHiveDrops() {
		IAlleleBeeSpecies valiantSpecies = Utils.getSpecies(BeeDefinition.VALIANT);
		EnumHiveType.Water.drops.add(new HiveDrop(ExtraBeesSpecies.WATER, 80));
		EnumHiveType.Water.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Rock.drops.add(new HiveDrop(ExtraBeesSpecies.ROCK, 80));
		EnumHiveType.Rock.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Nether.drops.add(new HiveDrop(ExtraBeesSpecies.BASALT, 80));
		EnumHiveType.Nether.drops.add(new HiveDrop(valiantSpecies, 3));
		EnumHiveType.Marble.drops.add(new HiveDrop(ExtraBeesSpecies.MARBLE, 80));
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
