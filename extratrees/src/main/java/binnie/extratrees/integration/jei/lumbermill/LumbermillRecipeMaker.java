package binnie.extratrees.integration.jei.lumbermill;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.ILumbermillManager;
import binnie.extratrees.api.recipes.ILumbermillRecipe;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class LumbermillRecipeMaker {
	public static List<LumbermillRecipeWrapper> create(IJeiHelpers helpers) {
		List<LumbermillRecipeWrapper> recipes = new ArrayList<>();

		ILumbermillManager lumbermillManager = ExtraTreesRecipeManager.lumbermillManager;
		if (lumbermillManager == null) {
			return recipes;
		}

		for (ILumbermillRecipe recipe : lumbermillManager.recipes()) {
			ItemStack logInput = recipe.getInput();
			ItemStack plankOutput = recipe.getOutput();
			recipes.add(new LumbermillRecipeWrapper(logInput, plankOutput));
		}

		NonNullList<ItemStack> oreDictLogs = OreDictionary.getOres("logWood");
		List<ItemStack> logSubtypes = helpers.getStackHelper().getAllSubtypes(oreDictLogs);

		for (ItemStack log : logSubtypes) {
			ItemStack plankStack = LumbermillRecipeManager.getRecipeWithPlanksOutput(log.copy(), null);
			if (!plankStack.isEmpty()) {
				plankStack.setCount((int) Math.ceil(plankStack.getCount() * 1.5f)); // turns stack of 4 up to 6
				recipes.add(new LumbermillRecipeWrapper(log.copy(), plankStack));
			}
		}

		return recipes;
	}
}
