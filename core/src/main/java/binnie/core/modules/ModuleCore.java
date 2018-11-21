package binnie.core.modules;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.modules.features.BinnieItems;
import binnie.core.util.OreDictUtils;
import binnie.core.util.RecipeUtil;

@ForestryModule(moduleID = BinnieCoreModuleUIDs.CORE, containerID = Constants.CORE_MOD_ID, coreModule = true, name = "Core", unlocalizedDescription = "binniecore.module.core")
public class ModuleCore extends BinnieModule {
	public ModuleCore() {
		super("forestry", "core");
	}

	@Override
	public void registerRecipes() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		recipeUtil.addRecipe("field_kit", BinnieItems.FIELD_KIT.stack(1, 63),
			"g  ",
			" is",
			" pi",
			'g', OreDictUtils.PANE_GLASS,
			'i', OreDictUtils.INGOT_IRON,
			'p', OreDictUtils.PAPER,
			's', OreDictUtils.DYE_BLACK);
	}
}
