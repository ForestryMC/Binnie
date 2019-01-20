package binnie.genetics.integration.jei.database;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.modules.features.GeneticItems;

import mezz.jei.api.recipe.IStackHelper;

public class DatabaseRecipeMaker {
	public static List<DatabaseRecipeWrapper> create() {
		List<DatabaseRecipeWrapper> recipes = new ArrayList<>();

		{
			ItemStack emptySerum = GeneticsItems.EMPTY_SERUM.stack(1);
			ItemStack resultSerum = GeneticItems.SERUM.stack(1, OreDictionary.WILDCARD_VALUE);
			IStackHelper stackHelper = GeneticsJeiPlugin.jeiHelpers.getStackHelper();
			List<ItemStack> subtypes = stackHelper.toItemStackList(resultSerum);
			List<ItemStack> resultSerums = new ArrayList<>();
			for (ItemStack subtype : subtypes) {
				subtype = subtype.copy();
				subtype.setItemDamage(subtype.getMaxDamage());
				resultSerums.add(subtype);
			}
			recipes.add(new DatabaseRecipeWrapper(emptySerum, resultSerums));
		}

		{
			ItemStack emptySerumArray = GeneticsItems.EMPTY_GENOME.stack(1);
			ItemStack resultSerumArray = GeneticItems.SERUM_ARRAY.stack(1, OreDictionary.WILDCARD_VALUE);
			IStackHelper stackHelper = GeneticsJeiPlugin.jeiHelpers.getStackHelper();
			List<ItemStack> subtypes = stackHelper.toItemStackList(resultSerumArray);
			List<ItemStack> resultSerumArrays = new ArrayList<>();
			for (ItemStack subtype : subtypes) {
				subtype = subtype.copy();
				subtype.setItemDamage(subtype.getMaxDamage());
				resultSerumArrays.add(subtype);
			}
			recipes.add(new DatabaseRecipeWrapper(emptySerumArray, resultSerumArrays));
		}

		return recipes;
	}
}
