package binnie.extratrees.integration.jei.brewery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.IBreweryRecipe;
import mezz.jei.api.ingredients.IIngredients;

public class BreweryRecipeWrapper implements IRecipeWrapper {
	private final IBreweryRecipe recipe;

	public BreweryRecipeWrapper(IBreweryRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		inputs.add(recipe.getIngredients());
		inputs.add(recipe.getGrains());
		inputs.add(Collections.singletonList(recipe.getYeast()));

		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setInput(FluidStack.class, recipe.getInput());
		ingredients.setOutput(FluidStack.class, recipe.getOutput());
	}
}
