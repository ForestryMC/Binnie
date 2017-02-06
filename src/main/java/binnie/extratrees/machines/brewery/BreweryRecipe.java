package binnie.extratrees.machines.brewery;

import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BreweryRecipe implements IBreweryRecipe {
	private final FluidStack input;
	private final FluidStack output;
	private final ItemStack yeast;

	public BreweryRecipe(final FluidStack input, final FluidStack output) {
		this(input, output, null);
	}

	public BreweryRecipe(final FluidStack input, final FluidStack output, @Nullable final ItemStack specificYeast) {
		this.input = input;
		this.output = output;
		if (specificYeast != null) {
			this.yeast = specificYeast;
		} else {
			this.yeast = ExtraTreeItems.Yeast.get(1);
		}
	}

	@Override
	public FluidStack getOutput(final BreweryCrafting crafting) {
		if (!this.yeast.isItemEqual(crafting.yeast)) {
			return null;
		}
		if (this.input.isFluidEqual(crafting.inputFluid)) {
			return this.output.copy();
		}
		return null;
	}

	@Override
	public FluidStack getInput() {
		return this.input;
	}

	@Override
	public FluidStack getOutput() {
		return output;
	}

	@Override
	public boolean isGrain(ItemStack itemStack) {
		return false;
	}

	@Override
	public List<ItemStack> getGrains() {
		return Collections.emptyList();
	}

	@Override
	public boolean isIngredient(ItemStack itemstack) {
		return false;
	}

	@Override
	public List<ItemStack> getIngredients() {
		return Collections.emptyList();
	}

	@Override
	public ItemStack getYeast() {
		return yeast;
	}
}
