package binnie.genetics.integration.jei.polymeriser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.core.features.StackOption;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.modules.features.GeneticItems;

import mezz.jei.api.recipe.IStackHelper;

public class PolymeriserRecipeMaker {
	public static List<PolymeriserRecipeWrapper> create() {
		List<PolymeriserRecipeWrapper> recipes = new ArrayList<>();

		List<ItemStack> inputs = Arrays.asList(
			GeneticItems.SEQUENCE.stack(StackOption.WILDCARD),
			GeneticItems.SERUM.stack(StackOption.WILDCARD),
			GeneticItems.SERUM_ARRAY.stack(StackOption.WILDCARD)
		);

		IStackHelper stackHelper = GeneticsJeiPlugin.jeiHelpers.getStackHelper();
		for (ItemStack input : inputs) {
			List<ItemStack> subtypes = stackHelper.toItemStackList(input);
			for (ItemStack subtype : subtypes) {
				subtype = subtype.copy();
				subtype.setItemDamage(subtype.getMaxDamage());
				recipes.add(new PolymeriserRecipeWrapper(subtype));
			}
		}

		return recipes;
	}
}
