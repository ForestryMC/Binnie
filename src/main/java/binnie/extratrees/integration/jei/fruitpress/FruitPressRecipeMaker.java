package binnie.extratrees.integration.jei.fruitpress;

import binnie.extratrees.machines.fruitpress.FruitPressRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FruitPressRecipeMaker {
	public static List<FruitPressRecipeWrapper> create() {
		List<FruitPressRecipeWrapper> recipes = new ArrayList<>();

		Collection<Pair<ItemStack, FluidStack>> recipePairs = FruitPressRecipes.getRecipes();
		for (Pair<ItemStack, FluidStack> recipePair : recipePairs) {
			ItemStack fruit = recipePair.getKey();
			FluidStack juice = recipePair.getValue();
			recipes.add(new FruitPressRecipeWrapper(fruit, juice));
		}

		return recipes;
	}
}
