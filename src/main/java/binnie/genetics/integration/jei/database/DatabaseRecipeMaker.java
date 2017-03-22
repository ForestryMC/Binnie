package binnie.genetics.integration.jei.database;

import binnie.genetics.Genetics;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ModuleItems;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRecipeMaker {
	public static List<DatabaseRecipeWrapper> create() {
		List<DatabaseRecipeWrapper> recipes = new ArrayList<>();
		ModuleItems items = Genetics.items();

		{
			ItemStack emptySerum = GeneticsItems.EmptySerum.get(1);
			ItemStack resultSerum = new ItemStack(items.itemSerum, 1, OreDictionary.WILDCARD_VALUE);
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
			ItemStack emptySerumArray = GeneticsItems.EmptyGenome.get(1);
			ItemStack resultSerumArray = new ItemStack(items.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE);
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
