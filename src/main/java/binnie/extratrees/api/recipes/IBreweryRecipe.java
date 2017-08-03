package binnie.extratrees.api.recipes;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.IBinnieRecipe;

public interface IBreweryRecipe extends IBinnieRecipe {
	@Nullable
	FluidStack getOutput(IBreweryCrafting crafting);

	FluidStack getInput();

	FluidStack getOutput();

	boolean isIngredient(ItemStack itemStack);

	List<ItemStack> getIngredients();

	boolean isGrain(ItemStack itemStack);

	List<ItemStack> getGrains();

	ItemStack getYeast();
}
