package binnie.extratrees.integration.jei.brewery;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.IBreweryRecipe;

import java.util.ArrayList;
import java.util.List;

public class BreweryRecipeMaker {
	public static List<BreweryRecipeWrapper> create() {
		List<BreweryRecipeWrapper> recipes = new ArrayList<>();

		if (ExtraTreesRecipeManager.breweryManager == null) {
			return recipes;
		}

		for (IBreweryRecipe breweryRecipe : ExtraTreesRecipeManager.breweryManager.recipes()) {
			recipes.add(new BreweryRecipeWrapper(breweryRecipe));
		}

		return recipes;
	}
}
