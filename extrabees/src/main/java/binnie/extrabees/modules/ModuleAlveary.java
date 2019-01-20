package binnie.extrabees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.circuits.ICircuitLayout;
import forestry.api.modules.ForestryModule;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraBeesModuleUIDs;
import binnie.core.util.RecipeUtil;
import binnie.extrabees.circuit.AlvearySimulatorCircuitType;
import binnie.extrabees.circuit.BinnieCircuitLayout;
import binnie.extrabees.circuit.BinnieCircuitSocketType;
import binnie.extrabees.machines.ExtraBeeMachines;
import binnie.extrabees.machines.TileExtraBeeAlveary;

@ForestryModule(moduleID = ExtraBeesModuleUIDs.ALVEARY, containerID = Constants.EXTRA_BEES_MOD_ID, name = "Alveary", unlocalizedDescription = "extrabees.module.alveary")
public class ModuleAlveary extends BinnieModule {

	public ModuleAlveary() {
		super(Constants.EXTRA_BEES_MOD_ID, ExtraBeesModuleUIDs.CORE);
	}

	@Override
	public void preInit() {
		BinnieCore.getBinnieProxy().registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary");
	}

	@Override
	public void registerRecipes() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		ItemStack alveary = Mods.Forestry.stack("alveary.plain");
		Item thermionicTubes = Mods.Forestry.item("thermionic_tubes");
		Item chipsets = Mods.Forestry.item("chipsets");

		recipeUtil.addRecipe("alveary_mutator", ExtraBeeMachines.MUTATOR.get(1), "g g", " a ", "t t", 'g', Items.GOLD_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		recipeUtil.addRecipe("alveary_frame", ExtraBeeMachines.FRAME.get(1), "iii", "tat", " t ", 'i', Items.IRON_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		recipeUtil.addRecipe("alveary_rain_shield", ExtraBeeMachines.RAIN_SHIELD.get(1), " b ", "bab", "t t", 'b', Items.BRICK, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		recipeUtil.addRecipe("alveary_lighting", ExtraBeeMachines.LIGHTING.get(1), "iii", "iai", " t ", 'i', Items.GLOWSTONE_DUST, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		recipeUtil.addRecipe("alveary_stimulator", ExtraBeeMachines.STIMULATOR.get(1), "kik", "iai", " t ", 'i', Items.GOLD_NUGGET, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4), 'k', new ItemStack(chipsets, 1, 2));
		recipeUtil.addRecipe("alveary_hatchery", ExtraBeeMachines.HATCHERY.get(1), "i i", " a ", "iti", 'i', Blocks.GLASS_PANE, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		recipeUtil.addRecipe("alveary_transmission", ExtraBeeMachines.TRANSMISSION.get(1), " t ", "tat", " t ", 'a', alveary, 't', "gearTin");
		ICircuitLayout stimulatorLayout = new BinnieCircuitLayout("Stimulator", BinnieCircuitSocketType.STIMULATOR);
		for (AlvearySimulatorCircuitType type : AlvearySimulatorCircuitType.values()) {
			type.createCircuit(stimulatorLayout);
		}
	}
}
