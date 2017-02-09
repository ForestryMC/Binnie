package binnie.genetics.integration.jei.incubator;

import binnie.Binnie;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.machine.incubator.IncubatorRecipe;
import binnie.genetics.machine.incubator.IncubatorRecipeLarvae;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			final IBee bee = Binnie.GENETICS.getBeeRoot().getMember(larvae);
			if (bee != null) {
				ItemStack drone = Binnie.GENETICS.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE);
				recipe.setOutputStack(drone);
				recipes.add(new LarvaeIncubatorRecipeWrapper(recipe));
			}
		}

		return recipes;
	}

}
