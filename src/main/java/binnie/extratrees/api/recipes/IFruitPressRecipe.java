package binnie.extratrees.api.recipes;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.IBinnieRecipe;

public interface IFruitPressRecipe extends IBinnieRecipe {

	ItemStack getInput();

	FluidStack getOutput();
}
