package binnie.extratrees.machines.infuser;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InfuserRecipes {

	private static final Map<Fluid, FluidStack> RECIPES = new HashMap<>();

	@Nullable
	public static FluidStack getOutput(final FluidStack fluid, final ItemStack stack) {
		return RECIPES.get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		return RECIPES.containsKey(fluid.getFluid());
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final Map.Entry<Fluid, FluidStack> entry : RECIPES.entrySet()) {
			if (entry.getValue().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output) {
		RECIPES.put(input.getFluid(), output);
	}
}
