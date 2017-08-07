package binnie.extrabees.init;

import binnie.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import forestry.api.circuits.ICircuitLayout;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.blocks.BlockAlvearyType;
import forestry.core.PluginCore;
import forestry.core.fluids.Fluids;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import binnie.extrabees.circuit.AlvearySimulatorCircuitType;
import binnie.extrabees.circuit.BinnieCircuitLayout;
import binnie.extrabees.circuit.BinnieCircuitSocketType;
import binnie.extrabees.items.types.EnumHiveFrame;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.EnumHoneyDrop;
import binnie.extrabees.items.types.EnumPropolis;
import binnie.extrabees.items.types.ExtraBeeItems;
import binnie.extrabees.utils.Utils;

public final class RecipeRegister {
	private static final RecipeUtil RECIPE_UTIL = new RecipeUtil(ExtraBees.MODID);
	
	public static void postInitRecipes() {
		if (Loader.isModLoaded("ic2")) {
			RECIPE_UTIL.addRecipe("honey_crystal", ExtraBees.honeyCrystal.getCharged(0), "#@#", "@#@", "#@#", '@', PluginApiculture.getItems().honeyDrop, '#', EnumHoneyDrop.ENERGY.get(1));
		}
		for (final EnumHoneyComb info : EnumHoneyComb.values()) {
			info.addRecipe();
		}
		for (final EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
			info2.addRecipe();
		}
		for (final EnumPropolis info3 : EnumPropolis.values()) {
			info3.addRecipe();
		}
		EnumHiveFrame.init(RECIPE_UTIL);
		addForestryRecipes();
		addForestryRecipes();
		addMiscItemRecipes();
		addAlvearyRecipes();
	}
	
	private static void addAlvearyRecipes() {
		ItemStack alveary = PluginApiculture.getBlocks().getAlvearyBlock(BlockAlvearyType.PLAIN);
		Item thermionicTubes = PluginCore.getItems().tubes;
		Item chipsets = PluginCore.getItems().circuitboards;
		
		RECIPE_UTIL.addRecipe("alveary_mutator", getAlvearyPart(EnumAlvearyLogicType.MUTATOR), "g g", " a ", "t t", 'g', Items.GOLD_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		RECIPE_UTIL.addRecipe("alveary_frame", getAlvearyPart(EnumAlvearyLogicType.FRAME), "iii", "tat", " t ", 'i', Items.IRON_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		RECIPE_UTIL.addRecipe("alveary_rain_shield", getAlvearyPart(EnumAlvearyLogicType.RAINSHIELD), " b ", "bab", "t t", 'b', Items.BRICK, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		RECIPE_UTIL.addRecipe("alveary_lighting", getAlvearyPart(EnumAlvearyLogicType.LIGHTING), "iii", "iai", " t ", 'i', Items.GLOWSTONE_DUST, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		RECIPE_UTIL.addRecipe("alveary_stimulator", getAlvearyPart(EnumAlvearyLogicType.STIMULATOR), "kik", "iai", " t ", 'i', Items.GOLD_NUGGET, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4), 'k', new ItemStack(chipsets, 1, 2));
		RECIPE_UTIL.addRecipe("alveary_hatchery", getAlvearyPart(EnumAlvearyLogicType.HATCHERY), "i i", " a ", "iti", 'i', Blocks.GLASS_PANE, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		RECIPE_UTIL.addRecipe("alveary_transmission", getAlvearyPart(EnumAlvearyLogicType.TRANSMISSION), " t ", "tat", " t ", 'a', alveary, 't', "gearTin");
		ICircuitLayout stimulatorLayout = new BinnieCircuitLayout("Stimulator", BinnieCircuitSocketType.STIMULATOR);
		for (final AlvearySimulatorCircuitType type : AlvearySimulatorCircuitType.values()) {
			type.createCircuit(stimulatorLayout);
		}
	}
	
	private static ItemStack getAlvearyPart(EnumAlvearyLogicType type) {
		return new ItemStack(ExtraBees.alveary, 1, type.ordinal());
	}
	
	private static void addForestryRecipes() {
		RecipeManagers.carpenterManager.addRecipe(100, Utils.getFluidFromName("water", 2000), ItemStack.EMPTY, new ItemStack(ExtraBees.dictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotTin", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}
	
	private static void addMiscItemRecipes() {
		final ItemStack lapisShard = ExtraBeeItems.LapisShard.get(1);
		RECIPE_UTIL.addShapelessRecipe("lapis_from_shards", new ItemStack(Items.DYE, 1, 4), lapisShard, lapisShard, lapisShard, lapisShard);
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
					RECIPE_UTIL.addShapelessRecipe(item.getModelPath() + "_dust", dust, input, input, input, input);
				} else if (ingot != null) {
					RECIPE_UTIL.addShapelessRecipe(item.getModelPath() + "_ingot", ingot, input, input, input, input, input, input, input, input, input);
				} else if (item == ExtraBeeItems.CoalDust) {
					RECIPE_UTIL.addShapelessRecipe("coal_dust_to_coal", new ItemStack(Items.COAL), input, input, input, input);
				}
			} else if (item.gemString != null) {
				ItemStack gem = null;
				if (!OreDictionary.getOres("gem" + item.gemString).isEmpty()) {
					gem = OreDictionary.getOres("gem" + item.gemString).get(0);
				}
				final ItemStack input2 = item.get(1);
				if (gem != null) {
					RECIPE_UTIL.addShapelessRecipe(item.getModelPath() + "_gem", gem.copy(), input2, input2, input2, input2, input2, input2, input2, input2, input2);
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
