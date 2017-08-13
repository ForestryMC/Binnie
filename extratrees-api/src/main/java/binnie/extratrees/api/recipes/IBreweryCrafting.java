package binnie.extratrees.api.recipes;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

public interface IBreweryCrafting {

	@Nullable
	FluidStack getInputFluid();

	@Nullable
	ItemStack[] getInputGrains();

	ItemStack getIngredient();

	ItemStack getYeast();

	boolean hasInputGrainsEmpty();
}
