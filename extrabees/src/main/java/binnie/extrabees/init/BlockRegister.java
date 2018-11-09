package binnie.extrabees.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.apiculture.genetics.BeeDefinition;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.TileEntityExtraBeesAlvearyPart;
import binnie.extrabees.blocks.BlockEctoplasm;
import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.extrabees.modules.ModuleCore;
import binnie.extrabees.utils.HiveDrop;
import binnie.extrabees.utils.Utils;
import binnie.extrabees.utils.config.ConfigurationMain;

public final class BlockRegister {

	public static void preInitBlocks() {
		ModuleCore.ectoplasm = new BlockEctoplasm();
		ExtraBees.proxy.registerBlock(ModuleCore.ectoplasm);
		ModuleCore.hive = new BlockExtraBeeHives();
		ExtraBees.proxy.registerBlock(ModuleCore.hive);
		ModuleCore.alveary = new BlockAlveary();
		ExtraBees.proxy.registerBlock(ModuleCore.alveary);
		GameRegistry.registerTileEntity(TileEntityExtraBeesAlvearyPart.class, "TileEntityExtraBeesAlvearyPart");
	}

	public static void doInitBlocks() {
		IAlleleBeeSpecies valiantSpecies = Utils.getSpecies(BeeDefinition.VALIANT);
		EnumHiveType.WATER.addDrops(
			new HiveDrop(ExtraBeeDefinition.WATER, 0.80).setIgnobleShare(0.5),
			new HiveDrop(valiantSpecies, 0.03).setIgnobleShare(0.5)
		);
		EnumHiveType.ROCK.addDrops(
			new HiveDrop(ExtraBeeDefinition.ROCK, 0.80).setIgnobleShare(0.5),
			new HiveDrop(valiantSpecies, 0.03).setIgnobleShare(0.5)
		);
		EnumHiveType.NETHER.addDrops(
			new HiveDrop(ExtraBeeDefinition.BASALT, 0.80).setIgnobleShare(0.5),
			new HiveDrop(valiantSpecies, 0.03).setIgnobleShare(0.5)
		);
		EnumHiveType.MARBLE.addDrops(
			new HiveDrop(ExtraBeeDefinition.MARBLE, 0.80).setIgnobleShare(0.5),
			new HiveDrop(valiantSpecies, 0.03).setIgnobleShare(0.5)
		);

		if (!ConfigurationMain.isCanQuarryMineHives()) {
			//BuildCraftAPI.softBlocks.add(ExtraBees.hive);
		}
	}
}
