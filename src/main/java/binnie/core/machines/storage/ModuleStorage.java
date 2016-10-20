// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.storage;

import net.minecraft.item.crafting.IRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.creativetab.CreativeTabs;
import binnie.core.machines.MachineGroup;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;

public class ModuleStorage implements IInitializable
{
	@Override
	public void preInit() {
		(BinnieCore.packageCompartment = new MachineGroup(BinnieCore.instance, "storage", "storage", Compartment.values())).setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		final String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "ingotIron" : "gearIron";
		final String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "ingotGold" : "gearGold";
		final String diamondGear = "gemDiamond";
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.Compartment.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Items.book, 'c', Blocks.chest, 'p', Blocks.stone_button }));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentCopper.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Compartment.Compartment.get(1), 'c', "gearCopper", 'p', Blocks.stone_button }));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentBronze.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Compartment.CompartmentCopper.get(1), 'c', "gearBronze", 'p', Items.gold_nugget }));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentIron.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Compartment.CompartmentCopper.get(1), 'c', ironGear, 'p', Items.gold_nugget }));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentGold.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Compartment.CompartmentIron.get(1), 'c', goldGear, 'p', Items.emerald }));
		GameRegistry.addRecipe(new ShapedOreRecipe(Compartment.CompartmentDiamond.get(1), new Object[] { "pcp", "cbc", "pcp", 'b', Compartment.CompartmentGold.get(1), 'c', diamondGear, 'p', Items.emerald }));
	}
}
