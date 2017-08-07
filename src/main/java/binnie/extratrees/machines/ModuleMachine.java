package binnie.extratrees.machines;

import binnie.Constants;
import binnie.core.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraftforge.oredict.ShapedOreRecipe;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.core.Tabs;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;
import binnie.extratrees.machines.nursery.TileEntityNursery;

public class ModuleMachine implements IInitializable {
	public static ValidatorSprite spritePolish;

	public Block blockMachine;

	@Override
	public void preInit() {
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
		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		recipeUtil.addRecipe("lumbermill", ExtraTreeMachine.Lumbermill.get(1), "gAg", "GsG", "gPg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'A', Items.IRON_AXE, 's', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze");
		recipeUtil.addRecipe("press", ExtraTreeMachine.Press.get(1), "iGi", "tSt", "tPt", 'i', "ingotIron", 'G', Blocks.GLASS, 't', "ingotTin", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze");
		recipeUtil.addRecipe("brewery", ExtraTreeMachine.BREWERY.get(1), "bGb", "iSi", "bPb", 'i', "ingotIron", 'G', Blocks.GLASS, 'b', "gearBronze", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze");
		recipeUtil.addRecipe("distillery", ExtraTreeMachine.Distillery.get(1), "rGr", "iSi", "rPr", 'i', "ingotIron", 'G', Blocks.GLASS, 'r', "dustRedstone", 'S', Mods.Forestry.stack("sturdy_machine"), 'P', "gearBronze");
		recipeUtil.addRecipe("woodworker", ExtraTreeMachine.Woodworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.PLANKS, 's', Mods.Forestry.stack("impregnated_casing"));
		recipeUtil.addRecipe("panelworker", ExtraTreeMachine.Panelworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.WOODEN_SLAB, 's', Mods.Forestry.stack("impregnated_casing"));
		recipeUtil.addRecipe("glassworker", ExtraTreeMachine.Glassworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.GLASS, 's', Mods.Forestry.stack("impregnated_casing"));
		recipeUtil.addRecipe("tileworker", ExtraTreeMachine.Tileworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Items.CLAY_BALL, 's', Mods.Forestry.stack("impregnated_casing"));
		LumbermillRecipeManager.calculateProducts();
	}
}
