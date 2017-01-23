package binnie.extratrees.integration.jei.brewery;

import binnie.extratrees.machines.brewery.BreweryRecipes;
import binnie.extratrees.machines.brewery.IBreweryRecipe;
import binnie.extratrees.machines.lumbermill.LumbermillRecipes;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
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
