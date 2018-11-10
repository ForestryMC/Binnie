package binnie.extrabees.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;

import binnie.core.Binnie;
import binnie.core.Mods;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.util.RecipeUtil;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.EnumHoneyDrop;
import binnie.extrabees.items.types.EnumPropolis;
import binnie.extrabees.items.types.ExtraBeeItems;
import binnie.extrabees.modules.ModuleCore;

public final class RecipeRegister {
	private static final RecipeUtil RECIPE_UTIL = new RecipeUtil(ExtraBees.MODID);

	public static void doInitRecipes() {
		if (Loader.isModLoaded("ic2")) {
			RECIPE_UTIL.addRecipe("honey_crystal", ModuleCore.honeyCrystal.getCharged(0), "#@#", "@#@", "#@#", '@', Mods.Forestry.item("honey_drop"), '#', EnumHoneyDrop.ENERGY.get(1));
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
		addForestryRecipes();
		addMiscItemRecipes();
	}

	private static void addForestryRecipes() {
		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000),
			ItemStack.EMPTY,
			new ItemStack(ModuleCore.dictionaryBees),
			"X#X", "YEY", "RDR",
			'#', Blocks.GLASS_PANE,
			'X', Items.GOLD_INGOT,
			'Y', "ingotTin",
			'R', Items.REDSTONE,
			'D', Items.DIAMOND,
			'E', Items.EMERALD
		);
	}

	private static void addMiscItemRecipes() {
		final ItemStack lapisShard = ExtraBeeItems.LAPIS_SHARD.get(1);
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
				} else if (item == ExtraBeeItems.COAL_DUST) {
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
		ItemStack gear;
		Item woodGear = ForgeRegistries.ITEMS.getValue(new ResourceLocation("buildcraftcore", "gear_wood"));
		if (woodGear != null) {
			gear = new ItemStack(woodGear, 1);
		} else {
			gear = new ItemStack(Blocks.PLANKS, 1);
		}
		RecipeManagers.carpenterManager.addRecipe(100, Fluids.FOR_HONEY.getFluid(500), ItemStack.EMPTY, ExtraBeeItems.SCENTED_GEAR.get(1), " j ", "bgb", " p ", 'j', Mods.Forestry.item("royal_jelly"), 'b', Mods.Forestry.item("beeswax"), 'p', Mods.Forestry.item("pollen"), 'g', gear);
	}
}
