package binnie.extratrees.machines.brewery;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.item.ExtraTreeItems;

public class BreweryRecipe implements IBreweryRecipe {
	private final FluidStack input;
	private final FluidStack output;
	private final ItemStack yeast;

	public BreweryRecipe(final FluidStack input, final FluidStack output) {
		this(input, output, ExtraTreeItems.Yeast.get(1));
	}

	public BreweryRecipe(final FluidStack input, final FluidStack output, final ItemStack specificYeast) {
		this.input = input;
		this.output = output;
		this.yeast = specificYeast;
	}

	@Override
	@Nullable
	public FluidStack getOutput(final BreweryCrafting crafting) {
		if (!this.yeast.isItemEqual(crafting.yeast)) {
			return null;
		}
		if(!crafting.hasInputGrainsEmpty()){
			return null;
		}
		if(!crafting.ingredient.isEmpty()){
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
