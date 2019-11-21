package binnie.extratrees.api.recipes;

import binnie.core.api.ICraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IBreweryManager extends ICraftingManager<IBreweryRecipe> {

	void addRecipe(FluidStack input, FluidStack output);

	void addRecipe(FluidStack input, FluidStack output, ItemStack yeast);

	void addGrainRecipe(String grainOreName, FluidStack output);

	void addGrainRecipe(String grainOreName, FluidStack output, String ingredientOreName);

	void addGrainRecipe(String grainOreName, FluidStack output, @Nullable String ingredientOreName, ItemStack yeast);
}
