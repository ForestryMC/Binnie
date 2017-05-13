package binnie.extrabees.init;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.circuit.AlvearySimulatorCircuitType;
import binnie.extrabees.items.types.*;
import binnie.extrabees.circuit.BinnieCircuitLayout;
import binnie.extrabees.circuit.BinnieCircuitSocketType;
import binnie.extrabees.utils.Utils;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.blocks.BlockAlvearyType;
import forestry.core.PluginCore;
import forestry.core.fluids.Fluids;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class RecipeRegister {

	public static void postInitRecipes(){
		GameRegistry.addRecipe(new ItemStack(ExtraBees.honeyCrystalEmpty), "#@#", "@#@", "#@#", '@', PluginApiculture.getItems().honeyDrop, '#', EnumHoneyDrop.ENERGY.get(1));
		for (final EnumHoneyComb info : EnumHoneyComb.values()) {
			info.addRecipe();
		}
		for (final EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
			info2.addRecipe();
		}
		for (final EnumPropolis info3 : EnumPropolis.values()) {
			info3.addRecipe();
		}
		EnumHiveFrame.init();
		addForestryRecipes();
		addMiscItemRecipes();
		addAlvearyRecipes();
	}

	private static void addAlvearyRecipes(){
		ItemStack alveary = PluginApiculture.getBlocks().getAlvearyBlock(BlockAlvearyType.PLAIN);
		Item thermionicTubes = PluginCore.getItems().tubes;
		Item chipsets = PluginCore.getItems().circuitboards;

		GameRegistry.addRecipe(AlvearyMachine.Mutator.get(1), "g g", " a ", "t t", 'g', Items.GOLD_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		GameRegistry.addRecipe(AlvearyMachine.Frame.get(1), "iii", "tat", " t ", 'i', Items.IRON_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.RainShield.get(1), " b ", "bab", "t t", 'b', Items.BRICK, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.Lighting.get(1), "iii", "iai", " t ", 'i', Items.GLOWSTONE_DUST, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.Stimulator.get(1), "kik", "iai", " t ", 'i', Items.GOLD_NUGGET, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4), 'k', new ItemStack(chipsets, 1, 2));
		GameRegistry.addRecipe(AlvearyMachine.Hatchery.get(1), "i i", " a ", "iti", 'i', Blocks.GLASS_PANE, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		GameRegistry.addRecipe(new ShapedOreRecipe(AlvearyMachine.Transmission.get(1), " t ", "tat", " t ", 'a', alveary, 't', "gearTin"));
		ICircuitLayout stimulatorLayout = new BinnieCircuitLayout("Stimulator", BinnieCircuitSocketType.STIMULATOR);
		for (final AlvearySimulatorCircuitType type : AlvearySimulatorCircuitType.values()) {
			type.createCircuit(stimulatorLayout);
		}
	}

	private static void addForestryRecipes(){
		RecipeManagers.carpenterManager.addRecipe(100, Utils.getFluidFromName("water", 2000), ItemStack.EMPTY, new ItemStack(ExtraBees.dictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotTin", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

	private static void addMiscItemRecipes(){
		final ItemStack lapisShard = ExtraBeeItems.LapisShard.get(1);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 4), lapisShard, lapisShard, lapisShard, lapisShard);
		for (final ExtraBeeItems item : ExtraBeeItems.values()) {
			if (item.metalString != null) {
				ItemStack dust = null;
				ItemStack ingot = null;
				if (!OreDictionary.getOres("ingot" + item.metalString).isEmpty()) {
					ingot = OreDictionary.getOres("ingot" + item.metalString).get(0).copy();
				}
				if (!OreDictionary.getOres("dust" + item.metalString).isEmpty()) {
					dust = OreDictionary.getOres("dust" + item.metalString).get(0).copy();
				}
				final ItemStack input = item.get(1);
				if (dust != null) {
					GameRegistry.addShapelessRecipe(dust, input, input, input, input);
				} else if (ingot != null) {
					GameRegistry.addShapelessRecipe(ingot, input, input, input, input, input, input, input, input, input);
				} else if (item == ExtraBeeItems.CoalDust) {
					GameRegistry.addShapelessRecipe(new ItemStack(Items.COAL), input, input, input, input);
				}
			} else if (item.gemString != null) {
				ItemStack gem = null;
				if (!OreDictionary.getOres("gem" + item.gemString).isEmpty()) {
					gem = OreDictionary.getOres("gem" + item.gemString).get(0);
				}
				final ItemStack input2 = item.get(1);
				if (gem != null) {
					GameRegistry.addShapelessRecipe(gem.copy(), input2, input2, input2, input2, input2, input2, input2, input2, input2);
				}
			}
		}
		Item woodGear = null;
		try {
			woodGear = (Item) Class.forName("buildcraft.BuildCraftCore").getField("woodenGearItem").get(null);
		} catch (Exception ignored) {
		}
		ItemStack gear = new ItemStack(Blocks.PLANKS, 1);
		if (woodGear != null) {
			gear = new ItemStack(woodGear, 1);
		}
		RecipeManagers.carpenterManager.addRecipe(100, Fluids.FOR_HONEY.getFluid(500), ItemStack.EMPTY, ExtraBeeItems.ScentedGear.get(1), " j ", "bgb", " p ", 'j', PluginApiculture.getItems().royalJelly, 'b', PluginCore.getItems().beeswax, 'p', PluginApiculture.getItems().pollenCluster, 'g', gear);

	}

}
