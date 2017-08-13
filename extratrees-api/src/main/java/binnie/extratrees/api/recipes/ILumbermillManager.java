package binnie.extratrees.api.recipes;

import net.minecraft.item.ItemStack;

import binnie.core.api.ICraftingManager;

public interface ILumbermillManager extends ICraftingManager<ILumbermillRecipe> {

	void addRecipe(ItemStack input, ItemStack output);
}
