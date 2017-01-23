package binnie.extratrees.machines.brewery;

import binnie.Binnie;
import binnie.core.util.OreDictionaryUtil;
import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BrewedGrainRecipe implements IBreweryRecipe {
	public static final FluidStack WATER = Binnie.Liquid.getFluidStack("water", 5);

	private final FluidStack output;
	private final int grainOreId;
	@Nullable
	private final Integer ingredientOreId;
	private final ItemStack yeast;

	public BrewedGrainRecipe(Alcohol output, int grainOreId) {
		this(output, grainOreId, null, null);
	}

	public BrewedGrainRecipe(Alcohol output, int grainOreId, Integer ingredientOreId) {
		this(output, grainOreId, ingredientOreId, null);
	}

	public BrewedGrainRecipe(Alcohol output, int grainOreId, @Nullable Integer ingredientOreId, @Nullable ItemStack specificYeast) {
		this.output = output.get(5);
		this.grainOreId = grainOreId;
		this.ingredientOreId = ingredientOreId;
		if (specificYeast != null) {
			this.yeast = specificYeast;
		} else {
			this.yeast = ExtraTreeItems.Yeast.get(1);
		}
	}

	@Override
	public FluidStack getOutput(final BreweryCrafting crafting) {
		if (!WATER.isFluidEqual(crafting.inputFluid)) {
			return null;
		}

		if (!isIngredient(crafting.ingredient)) {
			return null;
		}

		if (!yeast.isItemEqual(crafting.yeast)) {
			return null;
		}

		int grainCount = 0;
		for (final ItemStack stack : crafting.inputGrains) {
			if (stack == null) {
				return null;
			}
			if (isGrain(stack)) {
				grainCount++;
			}
		}

		if (grainCount >= 2) {
			return output.copy();
		} else {
			return null;
		}
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
	public boolean isIngredient(@Nullable ItemStack itemstack) {
		if (itemstack == null) {
			return ingredientOreId == null;
		}
		return ingredientOreId != null && OreDictionaryUtil.hasOreId(itemstack, ingredientOreId);
	}

	public List<ItemStack> getIngredients() {
		if (ingredientOreId == null) {
			return Collections.emptyList();
		}
		return OreDictionaryUtil.getOres(ingredientOreId);
	}

	@Override
	public boolean isGrain(@Nonnull ItemStack itemStack) {
		return OreDictionaryUtil.hasOreId(itemStack, grainOreId);
	}

	@Override
	public List<ItemStack> getGrains() {
		return OreDictionaryUtil.getOres(grainOreId);
	}

	@Override
	public ItemStack getYeast() {
		return yeast;
	}
}
