package binnie.extratrees.api.recipes;

import binnie.core.api.ICraftingManager;
import net.minecraft.item.ItemStack;

public interface ILumbermillManager extends ICraftingManager<ILumbermillRecipe> {

	void addRecipe(ItemStack input, ItemStack output);
}
