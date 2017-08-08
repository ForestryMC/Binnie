package binnie.extratrees.machines;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraftforge.oredict.ShapedOreRecipe;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.core.Tabs;

import binnie.Constants;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;
import binnie.extratrees.machines.nursery.TileEntityNursery;
import binnie.extratrees.modules.ExtraTreesModuleUIDs;
import binnie.modules.BinnieModule;
import binnie.modules.Module;
import binnie.modules.ModuleManager;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.MACHINES, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Machines", unlocalizedDescription = "extratrees.module.machines")
public class ModuleMachine extends Module {
	public static ValidatorSprite spritePolish;

	public static Block blockMachine;

	@Override
	public void setupAPI() {
		ExtraTreesRecipeManager.breweryManager = new BreweryRecipeManager();
		ExtraTreesRecipeManager.lumbermillManager = new LumbermillRecipeManager();
		ExtraTreesRecipeManager.fruitPressManager = new FruitPressRecipeManager();
		ExtraTreesRecipeManager.distilleryManager = new DistilleryRecipeManager();
	}

	@Override
	public void disabledSetupAPI() {
		super.disabledSetupAPI();
	}

	@Override
	public void registerItemsAndBlocks() {
		final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "machine", "machine", ExtraTreeMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		blockMachine = machineGroup.getBlock();
		// TODO fix rendering
		Object rendererMachine = null;// BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine");
		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityNursery.class, "binnie.tile.nursery", rendererMachine);
	}

	@Override
	public void init() {
		ModuleMachine.spritePolish = new ValidatorSprite(ExtraTrees.instance, "validator/polish.0", "validator/polish.1");
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Lumbermill.get(1), "gAg", "GsG", "gPg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'A', Items.IRON_AXE, 's', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Press.get(1), "iGi", "tSt", "tPt", 'i', "ingotIron", 'G', Blocks.GLASS, 't', "ingotTin", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.BREWERY.get(1), "bGb", "iSi", "bPb", 'i', "ingotIron", 'G', Blocks.GLASS, 'b', "gearBronze", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Distillery.get(1), "rGr", "iSi", "rPr", 'i', "ingotIron", 'G', Blocks.GLASS, 'r', "dustRedstone", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze"));
		if(ModuleManager.isEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)){
			GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Woodworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.PLANKS, 's', Mods.Forestry.stack("impregnated_casing")));
			GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Panelworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.WOODEN_SLAB, 's', Mods.Forestry.stack("impregnated_casing")));
			GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Glassworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.GLASS, 's', Mods.Forestry.stack("impregnated_casing")));
			GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Tileworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Items.CLAY_BALL, 's', Mods.Forestry.stack("impregnated_casing")));
		}
		LumbermillRecipeManager.calculateProducts();
	}
}
