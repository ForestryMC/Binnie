package binnie.botany.modules;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import forestry.api.modules.ForestryModule;

import binnie.botany.Botany;
import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.farming.CircuitGarden;
import binnie.botany.items.EnumTubeInsulate;
import binnie.botany.items.EnumTubeMaterial;
import binnie.botany.items.ItemInsulatedTube;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.util.RecipeUtil;

@ForestryModule(moduleID = BotanyModuleUIDs.FARMING, containerID = Constants.BOTANY_MOD_ID, name = "Farming", unlocalizedDescription = "botany.module.farming")
public class ModuleFarming extends BinnieModule {
	public ItemInsulatedTube insulatedTube;

	public ModuleFarming() {
		super(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.FLOWERS);
	}

	@Override
	public void registerItemsAndBlocks() {
		insulatedTube = new ItemInsulatedTube();
		Botany.proxy.registerItem(insulatedTube);
	}

	@Override
	public void doInit() {
		ItemStack yellow = new ItemStack(Blocks.YELLOW_FLOWER, 1);
		ItemStack red = new ItemStack(Blocks.RED_FLOWER, 1);
		ItemStack blue = new ItemStack(Blocks.RED_FLOWER, 1, 7);
		for (boolean manual : new boolean[]{true, false}) {
			for (boolean fertilised : new boolean[]{true, false}) {
				for (EnumMoisture moist : EnumMoisture.values()) {
					ItemStack icon;
					if (moist == EnumMoisture.DRY) {
						icon = yellow;
					} else if (moist == EnumMoisture.NORMAL) {
						icon = red;
					} else {
						icon = blue;
					}
					int insulate = 2 - moist.ordinal();
					if (fertilised) {
						insulate += 3;
					}
					new CircuitGarden(moist, null, manual, fertilised, new ItemStack(insulatedTube, 1, 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ACID, manual, fertilised, new ItemStack(insulatedTube, 1, 1 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.NEUTRAL, manual, fertilised, new ItemStack(insulatedTube, 1, 2 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ALKALINE, manual, fertilised, new ItemStack(insulatedTube, 1, 3 + 128 * insulate), icon);
				}
			}
		}

		for (EnumTubeMaterial mat : EnumTubeMaterial.VALUES) {
			RecipeUtil recipeUtil = new RecipeUtil(Constants.BOTANY_MOD_ID);
			for (EnumTubeInsulate insulate : EnumTubeInsulate.VALUES) {
				ItemStack tubes = new ItemStack(insulatedTube, 2, mat.ordinal() + 128 * insulate.ordinal());
				ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
				ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionic_tubes"), 1, mat.ordinal());
				String recipeName = "thermionic_tubes_" + insulate.getUid() + '_' + mat.getUid();
				recipeUtil.addShapelessRecipe(recipeName, tubes, forestryTube, forestryTube, insulateStack);
			}
		}
	}
}
