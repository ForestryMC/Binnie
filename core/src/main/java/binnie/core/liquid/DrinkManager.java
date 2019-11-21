package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DrinkManager {
	private static final Map<String, IDrinkLiquid> DRINK_LIQUIDS = new HashMap<>();

	@Nullable
	public static IDrinkLiquid getLiquid(final String id) {
		return DrinkManager.DRINK_LIQUIDS.get(id.toLowerCase());
	}

	public static void registerDrinkLiquid(final IDrinkLiquid liquid) {
		DrinkManager.DRINK_LIQUIDS.put(liquid.getIdentifier(), liquid);
	}

	@Nullable
	public static IDrinkLiquid getLiquid(final Fluid fluid) {
		return getLiquid(fluid.getName());
	}

	@Nullable
	public static IDrinkLiquid getLiquid(final FluidStack fluid) {
		return getLiquid(fluid.getFluid());
	}
}
