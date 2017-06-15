package binnie.extratrees.integration.jei.lumbermill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.ItemStack;

import binnie.extratrees.machines.lumbermill.LumbermillRecipes;

public class LumbermillRecipeMaker {
	public static List<LumbermillRecipeWrapper> create() {
		List<LumbermillRecipeWrapper> recipes = new ArrayList<>();

		Collection<Pair<ItemStack, ItemStack>> recipePairs = LumbermillRecipes.getRecipes();
		for (Pair<ItemStack, ItemStack> pair : recipePairs) {
			ItemStack logInput = pair.getKey();
			ItemStack plankOutput = pair.getValue();
			recipes.add(new LumbermillRecipeWrapper(logInput, plankOutput));
		}

		return recipes;
	}
}
