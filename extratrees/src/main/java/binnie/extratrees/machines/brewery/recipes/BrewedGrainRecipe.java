package binnie.extratrees.machines.brewery.recipes;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.util.FluidStackUtil;
import binnie.core.util.OreDictUtils;
import binnie.extratrees.api.recipes.IBreweryCrafting;
import binnie.extratrees.api.recipes.IBreweryRecipe;

public class BrewedGrainRecipe implements IBreweryRecipe {
	public static final FluidStack WATER = Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, Fluid.BUCKET_VOLUME);

	private final FluidStack output;
	private final String grainOreName;
	@Nullable
	private final String ingredientOreName;
	private final ItemStack yeast;

	public BrewedGrainRecipe(FluidStack output, String grainOreName, @Nullable String ingredientOreName, ItemStack specificYeast) {
		this.output = output;
		this.grainOreName = grainOreName;
		this.ingredientOreName = ingredientOreName;
		this.yeast = specificYeast;
	}

	@Override
	@Nullable
	public FluidStack getOutput(IBreweryCrafting crafting) {
		if (WATER.isFluidEqual(crafting.getInputFluid()) && isIngredient(crafting.getIngredient()) && yeast.isItemEqual(crafting.getYeast())) {
			int grainCount = 0;
			for (final ItemStack stack : crafting.getInputGrains()) {
				if (stack == null) {
					return null;
				}
				if (isGrain(stack)) {
					grainCount++;
				}
			}

			if (grainCount >= 2) {
				return output.copy();
			}
		}
		return null;
	}

	@Override
	public FluidStack getInput() {
		return WATER;
	}

	@Override
	public FluidStack getOutput() {
		return this.output;
	}


	@Override
	public Collection<Object> getInputs() {
		if (ingredientOreName == null) {
			return ImmutableList.of(grainOreName, yeast);
		}
		return ImmutableList.of(grainOreName, yeast, ingredientOreName);
	}

	@Override
	public Collection<Object> getOutputs() {
		return Collections.singleton(output);
	}

	@Override
	public boolean isIngredient(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return ingredientOreName == null;
		}
		return ingredientOreName != null && OreDictUtils.hasOreName(itemStack, ingredientOreName);
	}

	@Override
	public List<ItemStack> getIngredients() {
		if (ingredientOreName == null) {
			return Collections.emptyList();
		}
		return OreDictUtils.getOres(ingredientOreName);
	}

	@Override
	public boolean isGrain(ItemStack itemStack) {
		return !itemStack.isEmpty() && OreDictUtils.hasOreName(itemStack, grainOreName);
	}

	@Override
	public List<ItemStack> getGrains() {
		return OreDictUtils.getOres(grainOreName);
	}

	@Override
	public ItemStack getYeast() {
		return yeast;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("grainOreName", grainOreName)
			.add("output", FluidStackUtil.toString(output))
			.add("ingredientOreName", ingredientOreName)
			.add("yeast", yeast)
			.toString();
	}
}
