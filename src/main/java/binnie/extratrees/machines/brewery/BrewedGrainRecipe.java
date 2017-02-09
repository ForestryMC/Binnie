package binnie.extratrees.machines.brewery;

import binnie.Binnie;
import binnie.core.util.OreDictionaryUtil;
import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BrewedGrainRecipe implements IBreweryRecipe {
	public static final FluidStack WATER = Binnie.LIQUID.getFluidStack("water", Fluid.BUCKET_VOLUME);

	private final FluidStack output;
	private final int grainOreId;
	@Nullable
	private final Integer ingredientOreId;
	private final ItemStack yeast;

	public BrewedGrainRecipe(Alcohol output, int grainOreId) {
		this(output, grainOreId, null, ExtraTreeItems.Yeast.get(1));
	}

	public BrewedGrainRecipe(Alcohol output, int grainOreId, Integer ingredientOreId) {
		this(output, grainOreId, ingredientOreId, ExtraTreeItems.Yeast.get(1));
	}

	public BrewedGrainRecipe(Alcohol output, int grainOreId, @Nullable Integer ingredientOreId, ItemStack specificYeast) {
		this.output = output.get(Fluid.BUCKET_VOLUME);
		this.grainOreId = grainOreId;
		this.ingredientOreId = ingredientOreId;
		this.yeast = specificYeast;
	}

	@Override
	@Nullable
	public FluidStack getOutput(final BreweryCrafting crafting) {
		if (WATER.isFluidEqual(crafting.inputFluid) && isIngredient(crafting.ingredient) && yeast.isItemEqual(crafting.yeast)) {
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
	public boolean isIngredient(ItemStack itemstack) {
		if (itemstack.isEmpty()) {
			return ingredientOreId == null;
		}
		return ingredientOreId != null && OreDictionaryUtil.hasOreId(itemstack, ingredientOreId);
	}

	@Override
	public List<ItemStack> getIngredients() {
		if (ingredientOreId == null) {
			return Collections.emptyList();
		}
		return OreDictionaryUtil.getOres(ingredientOreId);
	}

	@Override
	public boolean isGrain(ItemStack itemStack) {
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
