package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface IBreweryRecipe {
	FluidStack getOutput(final BreweryCrafting crafting);

	FluidStack getInput();

	FluidStack getOutput();

	boolean isIngredient(@Nonnull ItemStack itemstack);

	List<ItemStack> getIngredients();

	boolean isGrain(@Nonnull ItemStack itemStack);

	List<ItemStack> getGrains();

	ItemStack getYeast();
}
