package binnie.core.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.machines.storage.Compartment;
import binnie.core.util.OreDictUtils;
import binnie.core.util.RecipeUtil;

@ForestryModule(moduleID = BinnieCoreModuleUIDs.STORAGE, containerID = Constants.CORE_MOD_ID, coreModule = true, name = "Storage", unlocalizedDescription = "binniecore.module.storage")
public class ModuleStorage extends BinnieModule {

	public ModuleStorage() {
		super(Constants.CORE_MOD_ID, BinnieCoreModuleUIDs.CORE);
	}

	@Override
	public void registerRecipes() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		String ironGear = OreDictUtils.getOrElse(OreDictUtils.GEAR_IRON, OreDictUtils.INGOT_IRON);
		String goldGear = OreDictUtils.getOrElse(OreDictUtils.GEAR_GOLD, OreDictUtils.INGOT_IRON);
		recipeUtil.addRecipe("compartment", Compartment.COMPARTMENT.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Items.BOOK,
			'c', Blocks.CHEST,
			'p', Blocks.STONE_BUTTON);
		recipeUtil.addRecipe("compartment_copper", Compartment.COMPARTMENT_COPPER.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Compartment.COMPARTMENT.get(1),
			'c', OreDictUtils.GEAR_COPPER,
			'p', Blocks.STONE_BUTTON);
		recipeUtil.addRecipe("compartment_bronze", Compartment.COMPARTMENT_BRONZE.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Compartment.COMPARTMENT_COPPER.get(1),
			'c', OreDictUtils.GEAR_BRONZE,
			'p', OreDictUtils.NUGGET_GOLD);
		recipeUtil.addRecipe("compartment_iron", Compartment.COMPARTMENT_IRON.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Compartment.COMPARTMENT_COPPER.get(1),
			'c', ironGear,
			'p', OreDictUtils.NUGGET_GOLD);
		recipeUtil.addRecipe("compartment_gold", Compartment.COMPARTMENT_GOLD.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Compartment.COMPARTMENT_IRON.get(1),
			'c', goldGear,
			'p', OreDictUtils.GEM_EMERALD);
		recipeUtil.addRecipe("compartment_diamond", Compartment.COMPARTMENT_DIAMOND.get(1),
			"pcp",
			"cbc",
			"pcp",
			'b', Compartment.COMPARTMENT_GOLD.get(1),
			'c', OreDictUtils.GEM_DIAMOND,
			'p', OreDictUtils.GEM_EMERALD);
	}
}
