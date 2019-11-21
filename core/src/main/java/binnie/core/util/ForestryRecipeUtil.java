package binnie.core.util;

import forestry.api.recipes.ICentrifugeRecipe;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public final class ForestryRecipeUtil {
	private ForestryRecipeUtil() {

	}

	public static void getCentrifugeOutputs(ItemStack inputStack, NonNullList<ItemStack> outputItems) {
		for (ICentrifugeRecipe recipe : RecipeManagers.centrifugeManager.recipes()) {
			if (inputStack.isItemEqual(recipe.getInput())) {
				outputItems.addAll(recipe.getAllProducts().keySet());
			}
		}
	}

	public static void getSqueezerOutputs(ItemStack inputStack, NonNullList<ItemStack> outputItems) {
		for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
			if (matches(recipe, inputStack)) {
				if (!recipe.getRemnants().isEmpty()) {
					outputItems.add(recipe.getRemnants());
				}
			}
		}
	}

	public static void getSqueezerFluidOutputs(ItemStack inputStack, NonNullList<FluidStack> outputFluids) {
		for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
			if (matches(recipe, inputStack)) {
				outputFluids.add(recipe.getFluidOutput());
			}
		}
	}

	private static boolean matches(ISqueezerRecipe recipe, ItemStack inputStack) {
		for (ItemStack obj : recipe.getResources()) {
			if (inputStack.isItemEqual(obj)) {
				return true;
			}
		}
		return false;
	}
}
