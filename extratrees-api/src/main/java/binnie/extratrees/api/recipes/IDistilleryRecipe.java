package binnie.extratrees.api.recipes;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.IBinnieRecipe;

public interface IDistilleryRecipe extends IBinnieRecipe {

	FluidStack getInput();

	FluidStack getOutput();

	int getLevel();
}
