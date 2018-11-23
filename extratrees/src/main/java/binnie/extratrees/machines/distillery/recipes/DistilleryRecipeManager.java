package binnie.extratrees.machines.distillery.recipes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.IDistilleryManager;
import binnie.extratrees.api.recipes.IDistilleryRecipe;

public class DistilleryRecipeManager implements IDistilleryManager {
	private static final int LEVELS = 3;
	private static final List<Map<Fluid, IDistilleryRecipe>> recipes = new ArrayList<>();
	private static final List<IDistilleryRecipe> recipeList = new ArrayList<>();

	static {
		for (int i = 0; i < LEVELS; i++) {
			recipes.add(new HashMap<>());
		}
	}

	@Nullable
	public static FluidStack getOutput(@Nullable FluidStack fluid, int level) {
		if (fluid != null) {
			Map<Fluid, IDistilleryRecipe> recipesForLevel = recipes.get(level);
			IDistilleryRecipe recipe = recipesForLevel.get(fluid.getFluid());
			if (recipe != null) {
				return recipe.getOutput().copy();
			}
		}
		return null;
	}

	public static boolean isValidInputLiquid(FluidStack fluid) {
		for (int i = 0; i < LEVELS; ++i) {
			Map<Fluid, IDistilleryRecipe> recipesForLevel = recipes.get(i);
			IDistilleryRecipe recipe = recipesForLevel.get(fluid.getFluid());
			if (recipe == null) {
				continue;
			}
			if (recipe.getInput().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(FluidStack fluid) {
		for (int i = 0; i < LEVELS; ++i) {
			Map<Fluid, IDistilleryRecipe> recipesForLevel = recipes.get(i);
			for (IDistilleryRecipe recipe : recipesForLevel.values()) {
				if (recipe.getOutput().isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean addRecipe(IDistilleryRecipe recipe) {
		Fluid input = recipe.getInput().getFluid();
		recipes.get(recipe.getLevel()).put(input, recipe);
		recipeList.add(recipe);
		return true;
	}

	@Override
	public boolean removeRecipe(IDistilleryRecipe recipe) {
		Fluid input = recipe.getInput().getFluid();
		recipes.get(recipe.getLevel()).remove(input, recipe.getOutput());
		return recipeList.remove(recipe);
	}

	public void addRecipe(FluidStack input, FluidStack output, int level) {
		addRecipe(new DistilleryRecipe(input, output, level));
	}

	public Collection<IDistilleryRecipe> recipes(int level) {
		Map<Fluid, IDistilleryRecipe> recipesForLevel = recipes.get(level);
		return Collections.unmodifiableCollection(recipesForLevel.values());
	}

	@Override
	public Collection<IDistilleryRecipe> recipes() {
		return recipeList;
	}
}
