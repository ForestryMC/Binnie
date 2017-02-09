package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreweryRecipes {
	public static final int GRAIN_BARLEY = OreDictionary.getOreID("seedBarley");
	public static final int GRAIN_WHEAT = OreDictionary.getOreID("seedWheat");
	public static final int GRAIN_RYE = OreDictionary.getOreID("seedRye");
	public static final int GRAIN_CORN = OreDictionary.getOreID("seedCorn");
	public static final int GRAIN_ROASTED = OreDictionary.getOreID("seedRoasted");
	public static final int HOPS = OreDictionary.getOreID("cropHops");

	private static List<IBreweryRecipe> recipes = new ArrayList<>();

	public static boolean isValidGrain(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			if (recipe.isGrain(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidYeast(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			ItemStack yeast = recipe.getYeast();
			if (yeast.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	public static FluidStack getOutput(final FluidStack stack) {
		final BreweryCrafting crafting = new BreweryCrafting(stack, ItemStack.EMPTY, null, ItemStack.EMPTY);
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack output = recipe.getOutput(crafting);
			if (output != null) {
				return output;
			}
		}
		return null;
	}

	@Nullable
	public static FluidStack getOutput(final BreweryCrafting crafting) {
		if (crafting.inputFluid != null && !crafting.yeast.isEmpty()) {
			for (final IBreweryRecipe recipe : recipes) {
				FluidStack output = recipe.getOutput(crafting);
				if (output != null) {
					return output;
				}
			}
		}
		return null;
	}

	public static boolean isValidIngredient(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			if (recipe.isIngredient(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack input = recipe.getInput();
			if (input.isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack output = recipe.getOutput();
			if (output.isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output) {
		recipes.add(new BreweryRecipe(input, output));
	}

	public static void addRecipe(IBreweryRecipe recipe) {
		recipes.add(recipe);
	}

	public static void addRecipes(IBreweryRecipe... recipes) {
		Collections.addAll(BreweryRecipes.recipes, recipes);
	}

	public static List<IBreweryRecipe> getRecipes() {
		return Collections.unmodifiableList(recipes);
	}
}
