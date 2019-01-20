package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import forestry.api.modules.ForestryModule;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.util.ModuleManager;
import binnie.core.util.OreDictUtils;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.items.ExtraTreeMiscItems;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;
import binnie.extratrees.machines.nursery.TileEntityNursery;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.MACHINES, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Machines", unlocalizedDescription = "extratrees.module.machines")
public class ModuleMachine extends BinnieModule {

	public ModuleMachine() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@Override
	public void setupAPI() {
		ExtraTreesRecipeManager.breweryManager = new BreweryRecipeManager();
		ExtraTreesRecipeManager.lumbermillManager = new LumbermillRecipeManager();
		ExtraTreesRecipeManager.fruitPressManager = new FruitPressRecipeManager();
		ExtraTreesRecipeManager.distilleryManager = new DistilleryRecipeManager();
	}

	@Override
	public void registerItemsAndBlocks() {
		// TODO fix rendering
		Object rendererMachine = null;// BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine");
		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityNursery.class, "binnie.tile.nursery");
	}

	@Override
	public void doInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		recipeUtil.addRecipe("lumbermill", ExtraTreeMachine.LUMBERMILL.get(1), "gAg", "GsG", "gPg", 'G', Blocks.GLASS, 'g', ExtraTreeMiscItems.PROVEN_GEAR.stack(1), 'A', Items.IRON_AXE, 's', Mods.Forestry.stack("sturdy_machine"), 'P', OreDictUtils.GEAR_BRONZE);
		recipeUtil.addRecipe("press", ExtraTreeMachine.PRESS.get(1), "iGi", "tSt", "tPt", 'i', OreDictUtils.INGOT_IRON, 'G', Blocks.GLASS, 't', "ingotTin", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', OreDictUtils.GEAR_BRONZE);
		recipeUtil.addRecipe("brewery", ExtraTreeMachine.BREWERY.get(1), "bGb", "iSi", "bPb", 'i', OreDictUtils.INGOT_IRON, 'G', Blocks.GLASS, 'b', OreDictUtils.GEAR_BRONZE, 'S', Mods.Forestry.stack("sturdy_machine"), 'P', OreDictUtils.GEAR_BRONZE);
		recipeUtil.addRecipe("distillery", ExtraTreeMachine.DISTILLERY.get(1), "rGr", "iSi", "rPr", 'i', OreDictUtils.INGOT_IRON, 'G', Blocks.GLASS, 'r', "dustRedstone", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', OreDictUtils.GEAR_BRONZE);
		if (ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)) {
			recipeUtil.addRecipe("woodworker", ExtraTreeMachine.WOODWORKER.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeMiscItems.PROVEN_GEAR.stack(1), 'w', Blocks.PLANKS, 's', Mods.Forestry.stack("impregnated_casing"));
			recipeUtil.addRecipe("panelworker", ExtraTreeMachine.PANELWORKER.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeMiscItems.PROVEN_GEAR.stack(1), 'w', Blocks.WOODEN_SLAB, 's', Mods.Forestry.stack("impregnated_casing"));
			recipeUtil.addRecipe("glassworker", ExtraTreeMachine.GLASSWORKER.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeMiscItems.PROVEN_GEAR.stack(1), 'w', Blocks.GLASS, 's', Mods.Forestry.stack("impregnated_casing"));
		}
	}
}
