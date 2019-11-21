package binnie.extratrees.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IBreweryCrafting {

	@Nullable
	FluidStack getInputFluid();

	@Nullable
	ItemStack[] getInputGrains();

	ItemStack getIngredient();

	ItemStack getYeast();

	boolean hasInputGrainsEmpty();
}
