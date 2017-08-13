package binnie.extratrees.api.recipes;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.ICraftingManager;

public interface IFruitPressManager extends ICraftingManager<IFruitPressRecipe> {

	void addRecipe(ItemStack input, FluidStack output);
}
