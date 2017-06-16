package binnie.extratrees.integration.jei.brewery;

import java.util.ArrayList;
import java.util.List;

import binnie.extratrees.machines.brewery.BreweryRecipes;
import binnie.extratrees.machines.brewery.IBreweryRecipe;

public class BreweryRecipeMaker {
	public static List<BreweryRecipeWrapper> create() {
		List<BreweryRecipeWrapper> recipes = new ArrayList<>();

		for (IBreweryRecipe breweryRecipe : BreweryRecipes.getRecipes()) {
			recipes.add(new BreweryRecipeWrapper(breweryRecipe));
		}

		return recipes;
	}
}
