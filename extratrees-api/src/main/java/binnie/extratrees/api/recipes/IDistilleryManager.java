package binnie.extratrees.api.recipes;

import java.util.Collection;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.ICraftingManager;

public interface IDistilleryManager extends ICraftingManager<IDistilleryRecipe> {

	void addRecipe(FluidStack input, FluidStack output, int level);

	Collection<IDistilleryRecipe> recipes(int level);
}
