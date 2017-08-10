package binnie.extratrees.integration.jei.lumbermill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.ILumbermillRecipe;

public class LumbermillRecipeMaker {
	public static List<LumbermillRecipeWrapper> create() {
		List<LumbermillRecipeWrapper> recipes = new ArrayList<>();

		if(ExtraTreesRecipeManager.lumbermillManager == null){
			return recipes;
		}

		Collection<ILumbermillRecipe> lumbermillRecipes = ExtraTreesRecipeManager.lumbermillManager.recipes();
		for (ILumbermillRecipe recipe : lumbermillRecipes) {
			ItemStack logInput = recipe.getInput();
			ItemStack plankOutput = recipe.getOutput();
			recipes.add(new LumbermillRecipeWrapper(logInput, plankOutput));
		}

		return recipes;
	}
}
