package binnie.genetics.integration.jei.polymeriser;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.modules.ModuleCore;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolymeriserRecipeMaker {
	public static List<PolymeriserRecipeWrapper> create() {
		List<PolymeriserRecipeWrapper> recipes = new ArrayList<>();

		List<ItemStack> inputs = Arrays.asList(
			new ItemStack(ModuleCore.itemSequencer, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack(ModuleCore.itemSerum, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack(ModuleCore.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE)
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
