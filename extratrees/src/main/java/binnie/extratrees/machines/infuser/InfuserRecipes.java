package binnie.extratrees.machines.infuser;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRecipes {

	private static final Map<Fluid, FluidStack> RECIPES = new HashMap<>();

	@Nullable
	public static FluidStack getOutput(FluidStack fluid, ItemStack stack) {
		return RECIPES.get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(FluidStack fluid) {
		return RECIPES.containsKey(fluid.getFluid());
	}

	public static boolean isValidOutputLiquid(FluidStack fluid) {
		for (Map.Entry<Fluid, FluidStack> entry : RECIPES.entrySet()) {
			if (entry.getValue().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(FluidStack input, FluidStack output) {
		RECIPES.put(input.getFluid(), output);
	}
}
