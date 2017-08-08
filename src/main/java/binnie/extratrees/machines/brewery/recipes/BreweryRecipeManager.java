package binnie.extratrees.machines.brewery.recipes;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import binnie.extratrees.api.recipes.IBreweryManager;
import binnie.extratrees.api.recipes.IBreweryRecipe;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.integration.jei.brewery.BreweryRecipeWrapper;
import binnie.extratrees.item.ExtraTreeItems;

public class BreweryRecipeManager implements IBreweryManager {

	private static Set<IBreweryRecipe> recipes = new HashSet<>();

	public static boolean isValidGrain(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			if (recipe.isGrain(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidYeast(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			ItemStack yeast = recipe.getYeast();
			if (yeast.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	public static FluidStack getOutput(final FluidStack stack) {
		final BreweryCrafting crafting = new BreweryCrafting(stack, ItemStack.EMPTY, null, ItemStack.EMPTY);
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack output = recipe.getOutput(crafting);
			if (output != null) {
				return output;
			}
		}
		return null;
	}

	@Nullable
	public static FluidStack getOutput(final BreweryCrafting crafting) {
		if (crafting.inputFluid != null && !crafting.yeast.isEmpty()) {
			for (final IBreweryRecipe recipe : recipes) {
				FluidStack output = recipe.getOutput(crafting);
				if (output != null) {
					return output;
				}
			}
		}
		return null;
	}
	
	@Nullable
	public static IBreweryRecipe getRecipe(final BreweryCrafting crafting) {
		if (crafting.inputFluid != null && !crafting.yeast.isEmpty()) {
			for (final IBreweryRecipe recipe : recipes) {
				FluidStack output = recipe.getOutput(crafting);
				if (output != null) {
					return recipe;
				}
			}
		}
		return null;
	}

	public static boolean isValidIngredient(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : recipes) {
			if (recipe.isIngredient(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack input = recipe.getInput();
			if (input.isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : recipes) {
			FluidStack output = recipe.getOutput();
			if (output.isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output) {
		addRecipe(input, output, ExtraTreeItems.YEAST.get(1));
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack yeast) {
		recipes.add(new BreweryRecipe(input, output, yeast));
	}

	@Override
	public void addGrainRecipe(String grainOreName, FluidStack output) {
		addGrainRecipe(grainOreName, output, null);
	}

	@Override
	public void addGrainRecipe(String grainOreName, FluidStack output, @Nullable String ingredientOreName) {
		addGrainRecipe(grainOreName, output, ingredientOreName, ExtraTreeItems.YEAST.get(1));
	}

	@Override
	public void addGrainRecipe(String grainOreName, FluidStack output, @Nullable String ingredientOreName, ItemStack yeast) {
		recipes.add(new BrewedGrainRecipe(output, grainOreName, ingredientOreName, yeast));
	}

	@Override
	public boolean addRecipe(IBreweryRecipe recipe) {
		return recipes.add(recipe);
	}

	@Override
	public boolean removeRecipe(IBreweryRecipe recipe) {
		return recipes.remove(recipe);
	}

	@Override
	public Collection<IBreweryRecipe> recipes() {
		return recipes;
	}

	@Override
	public String toString() {
		return "Brewery";
	}

	@Override
	public String getJEICategory() {
		return RecipeUids.BREWING;
	}

	@Nullable
	@Override
	public Object getJeiWrapper(IBreweryRecipe recipe) {
		if(!Loader.isModLoaded("jei")){
			return null;
		}
		return getWrapper(recipe);
	}

	@Optional.Method(modid = "jei")
	private Object getWrapper(IBreweryRecipe recipe){
		return new BreweryRecipeWrapper(recipe);
	}

}
