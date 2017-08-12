package binnie.genetics.integration.jei.incubator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forestry.api.apiculture.BeeManager;
import net.minecraft.item.ItemStack;

import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.machine.incubator.IncubatorRecipe;
import binnie.genetics.machine.incubator.IncubatorRecipeLarvae;

public class LarvaeIncubatorRecipeMaker {
	public static List<LarvaeIncubatorRecipeWrapper> create(@Nullable IncubatorRecipeLarvae recipeLarvae) {
		if (recipeLarvae == null) {
			return Collections.emptyList();
		}

		List<LarvaeIncubatorRecipeWrapper> recipes = new ArrayList<>();

		ItemStack inputStack = recipeLarvae.getInputStack();
		List<ItemStack> subtypes = GeneticsJeiPlugin.jeiHelpers.getStackHelper().getSubtypes(inputStack);
		for (ItemStack larvae : subtypes) {
			IncubatorRecipe recipe = new IncubatorRecipe(larvae, recipeLarvae.getInput(), recipeLarvae.getOutput(), recipeLarvae.getLossChance(), recipeLarvae.getChance());
			final IBee bee = BeeManager.beeRoot.getMember(larvae);
			if (bee != null) {
				ItemStack drone = BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.DRONE);
				recipe.setOutputStack(drone);
				recipes.add(new LarvaeIncubatorRecipeWrapper(recipe));
			}
		}

		return recipes;
	}
}
