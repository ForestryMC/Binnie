package binnie.extratrees.api.recipes;

import net.minecraft.item.ItemStack;

import binnie.core.api.IBinnieRecipe;

public interface ILumbermillRecipe extends IBinnieRecipe {

	ItemStack getOutput();

	ItemStack getInput();
}
