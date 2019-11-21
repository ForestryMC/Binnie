package binnie.extratrees.api.recipes;

import binnie.core.api.ICraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFruitPressManager extends ICraftingManager<IFruitPressRecipe> {

	void addRecipe(ItemStack input, FluidStack output);
}
