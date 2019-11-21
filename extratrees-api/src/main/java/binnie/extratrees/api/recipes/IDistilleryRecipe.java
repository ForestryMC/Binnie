package binnie.extratrees.api.recipes;

import binnie.core.api.IBinnieRecipe;
import net.minecraftforge.fluids.FluidStack;

public interface IDistilleryRecipe extends IBinnieRecipe {

	FluidStack getInput();

	FluidStack getOutput();

	int getLevel();
}
