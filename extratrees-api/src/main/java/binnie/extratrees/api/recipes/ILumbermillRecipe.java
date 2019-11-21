package binnie.extratrees.api.recipes;

import binnie.core.api.IBinnieRecipe;
import net.minecraft.item.ItemStack;

public interface ILumbermillRecipe extends IBinnieRecipe {

	ItemStack getOutput();

	ItemStack getInput();
}
