package binnie.extratrees.machines;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nullable;
import java.util.Set;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.IBinnieRecipe;
import binnie.core.api.ICraftingManager;
import binnie.extratrees.api.recipes.IBreweryManager;
import binnie.extratrees.api.recipes.IBreweryRecipe;
import binnie.extratrees.api.recipes.IDistilleryManager;
import binnie.extratrees.api.recipes.IDistilleryRecipe;
import binnie.extratrees.api.recipes.IFruitPressManager;
import binnie.extratrees.api.recipes.IFruitPressRecipe;
import binnie.extratrees.api.recipes.ILumbermillManager;
import binnie.extratrees.api.recipes.ILumbermillRecipe;

public class DummyManagers {

	public static abstract class DummyCraftingManager<T extends IBinnieRecipe> implements ICraftingManager<T> {
		@Override
		public boolean addRecipe(T recipe) {
			return false;
		}

		@Override
		public boolean removeRecipe(T recipe) {
			return false;
		}

		@Override
		public Set<T> recipes() {
			return ImmutableSet.of();
		}

		@Nullable
		@Override
		public String getJEICategory() {
			return null;
		}

		@Nullable
		@Override
		public Object getJeiWrapper(T recipe) {
			return null;
		}
	}

	public static class DummyBreweryManager extends DummyCraftingManager<IBreweryRecipe> implements IBreweryManager {
		@Override
		public void addRecipe(FluidStack input, FluidStack output) {
		}

		@Override
		public void addRecipe(FluidStack input, FluidStack output, ItemStack yeast) {
		}

		@Override
		public void addGrainRecipe(String grainOreName, FluidStack output) {
		}

		@Override
		public void addGrainRecipe(String grainOreName, FluidStack output, String ingredientOreName) {
		}

		@Override
		public void addGrainRecipe(String grainOreName, FluidStack output, @Nullable String ingredientOreName, ItemStack yeast) {
		}
	}

	public static class DummyLumbermillManager extends DummyCraftingManager<ILumbermillRecipe> implements ILumbermillManager {
		@Override
		public void addRecipe(ItemStack input, ItemStack output) {

		}
	}

	public static class DummyFruitPressManager extends DummyCraftingManager<IFruitPressRecipe> implements IFruitPressManager {
		@Override
		public void addRecipe(ItemStack input, FluidStack output) {

		}
	}

	public static class DummyDistilleryManager extends DummyCraftingManager<IDistilleryRecipe> implements IDistilleryManager {
		@Override
		public void addRecipe(FluidStack input, FluidStack output, int level) {
		}

		@Override
		public Set<IDistilleryRecipe> recipes(int level) {
			return ImmutableSet.of();
		}
	}
}
