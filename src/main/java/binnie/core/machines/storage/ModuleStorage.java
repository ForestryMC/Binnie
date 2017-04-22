package binnie.core.machines.storage;

import binnie.core.*;
import binnie.core.machines.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraftforge.oredict.*;

public class ModuleStorage implements IInitializable {
	@Override
	public void preInit() {
		BinnieCore.packageCompartment = new MachineGroup(BinnieCore.instance, "storage", "storage", Compartment.values());
		BinnieCore.packageCompartment.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "ingotIron" : "gearIron";
		String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "ingotGold" : "gearGold";
		String diamondGear = "gemDiamond";

		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.Compartment.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Items.book,
			'c', Blocks.chest,
			'p', Blocks.stone_button
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentCopper.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Compartment.Compartment.get(1),
			'c', "gearCopper",
			'p', Blocks.stone_button
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentBronze.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Compartment.CompartmentCopper.get(1),
			'c', "gearBronze",
			'p', Items.gold_nugget
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentIron.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Compartment.CompartmentCopper.get(1),
			'c', ironGear,
			'p', Items.gold_nugget
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentGold.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Compartment.CompartmentIron.get(1),
			'c', goldGear,
			'p', Items.emerald
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentDiamond.get(1), new Object[]{
			"pcp", "cbc", "pcp",
			'b', Compartment.CompartmentGold.get(1),
			'c', diamondGear,
			'p', Items.emerald
		}));
	}
}
