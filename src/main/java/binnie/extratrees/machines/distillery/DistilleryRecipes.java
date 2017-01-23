package binnie.extratrees.machines.distillery;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistilleryRecipes {
	private static final List<Map<Fluid, FluidStack>> recipes = new ArrayList<>();

	static {
		recipes.add(new HashMap<>());
		recipes.add(new HashMap<>());
		recipes.add(new HashMap<>());
	}

	public static FluidStack getOutput(@Nullable final FluidStack fluid, final int level) {
		if (fluid == null) {
			return null;
		}
		return recipes.get(level).get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			if (recipes.get(i).containsKey(fluid.getFluid())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			for (final Map.Entry<Fluid, FluidStack> entry : recipes.get(i).entrySet()) {
				if (entry.getValue().isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output, final int level) {
		recipes.get(level).put(input.getFluid(), output);
	}
}
