package binnie.genetics.integration.jei.database;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.modules.ModuleCore;

import mezz.jei.api.recipe.IStackHelper;

public class DatabaseRecipeMaker {
	public static List<DatabaseRecipeWrapper> create() {
		List<DatabaseRecipeWrapper> recipes = new ArrayList<>();

		{
			ItemStack emptySerum = GeneticsItems.EMPTY_SERUM.get(1);
			ItemStack resultSerum = new ItemStack(ModuleCore.itemSerum, 1, OreDictionary.WILDCARD_VALUE);
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
			ItemStack emptySerumArray = GeneticsItems.EMPTY_GENOME.get(1);
			ItemStack resultSerumArray = new ItemStack(ModuleCore.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE);
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
