package binnie.extratrees.integration.jei.brewery;

import binnie.extratrees.machines.brewery.BreweryRecipes;
import binnie.extratrees.machines.brewery.IBreweryRecipe;
import java.util.ArrayList;
import java.util.List;

public class BreweryRecipeMaker {
	public static List<BreweryRecipeWrapper> create() {
		List<BreweryRecipeWrapper> recipes = new ArrayList<>();

		for (IBreweryRecipe breweryRecipe : BreweryRecipes.getRecipes()) {
			recipes.add(new BreweryRecipeWrapper(breweryRecipe));
		}

		return recipes;
	}
}
