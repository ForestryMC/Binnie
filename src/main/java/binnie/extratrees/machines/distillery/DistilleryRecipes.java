package binnie.extratrees.machines.distillery;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

public class DistilleryRecipes {
	private static final List<Map<Fluid, Pair<FluidStack, FluidStack>>> recipes = new ArrayList<>();

	static {
		recipes.add(new HashMap<>());
		recipes.add(new HashMap<>());
		recipes.add(new HashMap<>());
	}

	@Nullable
	public static FluidStack getOutput(@Nullable final FluidStack fluid, final int level) {
		if (fluid != null) {
			Map<Fluid, Pair<FluidStack, FluidStack>> recipesForLevel = recipes.get(level);
			Pair<FluidStack, FluidStack> recipe = recipesForLevel.get(fluid.getFluid());
			if (recipe != null) {
				return recipe.getValue().copy();
			}
		}
		return null;
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			Map<Fluid, Pair<FluidStack, FluidStack>> recipesForLevel = recipes.get(i);
			Pair<FluidStack, FluidStack> recipe = recipesForLevel.get(fluid.getFluid());
			if (recipe.getKey().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			Map<Fluid, Pair<FluidStack, FluidStack>> recipesForLevel = recipes.get(i);
			for (final Pair<FluidStack, FluidStack> recipe : recipesForLevel.values()) {
				if (recipe.getValue().isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output, final int level) {
		Map<Fluid, Pair<FluidStack, FluidStack>> recipesForLevel = recipes.get(level);
		recipesForLevel.put(input.getFluid(), Pair.of(input, output));
	}

	public static Collection<Pair<FluidStack, FluidStack>> getRecipes(int level) {
		Map<Fluid, Pair<FluidStack, FluidStack>> recipesForLevel = recipes.get(level);
		return Collections.unmodifiableCollection(recipesForLevel.values());
	}
}
