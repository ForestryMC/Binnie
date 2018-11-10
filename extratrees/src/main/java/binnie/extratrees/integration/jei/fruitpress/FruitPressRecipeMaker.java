package binnie.extratrees.integration.jei.fruitpress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.IFruitPressRecipe;

public class FruitPressRecipeMaker {
	public static List<FruitPressRecipeWrapper> create() {
		List<FruitPressRecipeWrapper> recipes = new ArrayList<>();

		if (ExtraTreesRecipeManager.fruitPressManager == null) {
			return recipes;
		}

		Collection<IFruitPressRecipe> fruitPressRecipes = ExtraTreesRecipeManager.fruitPressManager.recipes();
		for (IFruitPressRecipe recipe : fruitPressRecipes) {
			ItemStack fruit = recipe.getInput();
			FluidStack juice = recipe.getOutput();
			recipes.add(new FruitPressRecipeWrapper(fruit, juice));
		}

		return recipes;
	}
}
