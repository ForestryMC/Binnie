package binnie.core.machines.storage;

import binnie.core.Constants;
import binnie.core.util.RecipeUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraftforge.oredict.OreDictionary;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.machines.MachineGroup;

public class ModuleStorage implements IInitializable {
	@Override
	public void preInit() {
		MachineGroup machineGroup = new MachineGroup(BinnieCore.getInstance(), "machine.storage", "storage", Compartment.values());
		BinnieCore.setPackageCompartment(machineGroup);
		machineGroup.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		final String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "ingotIron" : "gearIron";
		final String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "ingotGold" : "gearGold";
		final String diamondGear = "gemDiamond";
		recipeUtil.addRecipe("compartment", Compartment.Compartment.get(1), "pcp", "cbc", "pcp", 'b', Items.BOOK, 'c', Blocks.CHEST, 'p', Blocks.STONE_BUTTON);
		recipeUtil.addRecipe("compartment_copper", Compartment.CompartmentCopper.get(1), "pcp", "cbc", "pcp", 'b', Compartment.Compartment.get(1), 'c', "gearCopper", 'p', Blocks.STONE_BUTTON);
		recipeUtil.addRecipe("compartment_bronze", Compartment.CompartmentBronze.get(1), "pcp", "cbc", "pcp", 'b', Compartment.CompartmentCopper.get(1), 'c', "gearBronze", 'p', Items.GOLD_NUGGET);
		recipeUtil.addRecipe("compartment_iron", Compartment.CompartmentIron.get(1), "pcp", "cbc", "pcp", 'b', Compartment.CompartmentCopper.get(1), 'c', ironGear, 'p', Items.GOLD_NUGGET);
		recipeUtil.addRecipe("compartment_gold", Compartment.CompartmentGold.get(1), "pcp", "cbc", "pcp", 'b', Compartment.CompartmentIron.get(1), 'c', goldGear, 'p', Items.EMERALD);
		recipeUtil.addRecipe("compartment_diamond", Compartment.CompartmentDiamond.get(1), "pcp", "cbc", "pcp", 'b', Compartment.CompartmentGold.get(1), 'c', diamondGear, 'p', Items.EMERALD);
	}
}
