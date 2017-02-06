package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IBreweryRecipe {
	@Nullable
	FluidStack getOutput(final BreweryCrafting crafting);

	FluidStack getInput();

	FluidStack getOutput();

	boolean isIngredient(ItemStack itemstack);

	List<ItemStack> getIngredients();

	boolean isGrain(ItemStack itemStack);

	List<ItemStack> getGrains();

	ItemStack getYeast();
}
