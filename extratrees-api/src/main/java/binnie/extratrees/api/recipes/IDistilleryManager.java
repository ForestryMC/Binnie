package binnie.extratrees.api.recipes;

import binnie.core.api.ICraftingManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

public interface IDistilleryManager extends ICraftingManager<IDistilleryRecipe> {

	void addRecipe(FluidStack input, FluidStack output, int level);

	Collection<IDistilleryRecipe> recipes(int level);
}
