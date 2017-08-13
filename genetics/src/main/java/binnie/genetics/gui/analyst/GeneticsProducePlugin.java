package binnie.genetics.gui.analyst;

import binnie.core.util.ForestryRecipeUtil;
import binnie.genetics.api.IProducePlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GeneticsProducePlugin implements IProducePlugin {
	@Override
	public void getFluids(ItemStack inputStack, NonNullList<FluidStack> outputFluids) {
		ForestryRecipeUtil.getSqueezerFluidOutputs(inputStack, outputFluids);
	}

	@Override
	public void getFluids(FluidStack inputFluid, NonNullList<FluidStack> outputFluids) {

	}

	@Override
	public void getItems(ItemStack inputStack, NonNullList<ItemStack> outputItems) {
		ForestryRecipeUtil.getCentrifugeOutputs(inputStack, outputItems);
		ForestryRecipeUtil.getSqueezerOutputs(inputStack, outputItems);
		ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(inputStack);
		if (!smeltingResult.isEmpty()) {
			outputItems.add(smeltingResult);
		}
		getCrafting(inputStack, outputItems);
	}

	/**
	 * Get all recipes that has inputStack as its only ingredient, and return their outputs.
	 */
	private static void getCrafting(ItemStack inputStack, NonNullList<ItemStack> outputItems) {
		for (IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {
			ItemStack recipeOutput = recipe.getRecipeOutput();
			if (!recipeOutput.isEmpty() && matches(recipe, inputStack)) {
				outputItems.add(recipeOutput);
			}
		}
	}

	private static boolean matches(IRecipe recipe, ItemStack inputStack) {
		NonNullList<Ingredient> ingredients = recipe.getIngredients();
		if (ingredients.isEmpty()) {
			return false;
		}
		for (Ingredient ingredient : ingredients) {
			if (!ingredient.apply(inputStack)) {
				return false;
			}
		}
		return true;
	}
}
