package binnie.extratrees.integration.jei.brewery;

import binnie.extratrees.machines.brewery.IBreweryRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreweryRecipeWrapper extends BlankRecipeWrapper {
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
