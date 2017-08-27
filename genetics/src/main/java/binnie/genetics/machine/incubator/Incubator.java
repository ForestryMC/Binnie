package binnie.genetics.machine.incubator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;

public class Incubator {
	public static final int[] SLOT_QUEUE = new int[]{0, 1, 2};
	public static final int SLOT_INCUBATOR = 3;
	public static final int[] SLOT_OUTPUT = new int[]{4, 5, 6};
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;
	private static final List<IIncubatorRecipe> RECIPES = new ArrayList<>();
	@Nullable
	private static IncubatorRecipeLarvae LARVAE_RECIPE;

	public static void addRecipes() {
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), new FluidStack(FluidRegistry.WATER, 25), GeneticLiquid.GrowthMedium.get(25), 0.2f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.WHEAT), GeneticLiquid.GrowthMedium.get(25), GeneticLiquid.Bacteria.get(5), 0.2f));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.Bacteria.get(0), GeneticLiquid.Bacteria.get(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.SUGAR), GeneticLiquid.Bacteria.get(2), null, 0.5f, 0.2f)
			.setOutputStack(GeneticsItems.Enzyme.get(1)));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.BacteriaPoly.get(0), GeneticLiquid.BacteriaPoly.get(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.BacteriaVector.get(0), GeneticLiquid.BacteriaVector.get(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.DYE, 1, 15), GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaPoly.get(10), 0.1f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.BLAZE_POWDER), GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaVector.get(10), 0.05f));
		if (BinnieCore.isApicultureActive()) {
			final ItemStack beeLarvaeWildcard = Mods.Forestry.stack("bee_larvae_ge", 1, OreDictionary.WILDCARD_VALUE);
			LARVAE_RECIPE = new IncubatorRecipeLarvae(beeLarvaeWildcard);
			Incubator.RECIPES.add(LARVAE_RECIPE);
		}
	}

	public static List<IIncubatorRecipe> getRecipes() {
		return Collections.unmodifiableList(RECIPES);
	}

	@Nullable
	public static IncubatorRecipeLarvae getLarvaeRecipe() {
		return LARVAE_RECIPE;
	}
}
