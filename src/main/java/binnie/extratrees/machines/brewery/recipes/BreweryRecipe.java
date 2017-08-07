package binnie.extratrees.machines.brewery.recipes;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.util.FluidStackUtil;
import binnie.extratrees.api.recipes.IBreweryCrafting;
import binnie.extratrees.api.recipes.IBreweryRecipe;

public class BreweryRecipe implements IBreweryRecipe {
	private final FluidStack input;
	private final FluidStack output;
	private final ItemStack yeast;

	public BreweryRecipe(FluidStack input, FluidStack output, ItemStack specificYeast) {
		this.input = input;
		this.output = output;
		this.yeast = specificYeast;
	}

	@Override
	@Nullable
	public FluidStack getOutput(IBreweryCrafting crafting) {
		if (!this.yeast.isItemEqual(crafting.getYeast())) {
			return null;
		}
		if(!crafting.hasInputGrainsEmpty()){
			return null;
		}
		if(!crafting.getIngredient().isEmpty()){
			return null;
		}
		if (this.input.isFluidEqual(crafting.getInputFluid())) {
			return this.output.copy();
		}
		return null;
	}

	@Override
	public Collection<Object> getInputs() {
		return ImmutableList.of(input, yeast);
	}

	@Override
	public Collection<Object> getOutputs() {
		return Collections.singleton(output);
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
	public boolean isIngredient(ItemStack itemStack) {
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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("input", FluidStackUtil.toString(input))
			.add("output", FluidStackUtil.toString(output))
			.add("yeast", yeast)
			.toString();
	}
}
