package binnie.extratrees.machines.infuser;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRecipes {

	static Map<Fluid, FluidStack> recipes = new HashMap<>();

	@Nullable
	public static FluidStack getOutput(final FluidStack fluid, final ItemStack stack) {
		return recipes.get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		return recipes.containsKey(fluid.getFluid());
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final Map.Entry<Fluid, FluidStack> entry : recipes.entrySet()) {
			if (entry.getValue().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output) {
		recipes.put(input.getFluid(), output);
	}
}
