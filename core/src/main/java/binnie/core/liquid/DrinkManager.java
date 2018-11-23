package binnie.core.liquid;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class DrinkManager {
	private static final Map<String, IDrinkLiquid> DRINK_LIQUIDS = new HashMap<>();

	@Nullable
	public static IDrinkLiquid getLiquid(String id) {
		return DrinkManager.DRINK_LIQUIDS.get(id.toLowerCase());
	}

	public static void registerDrinkLiquid(IDrinkLiquid liquid) {
		DrinkManager.DRINK_LIQUIDS.put(liquid.getIdentifier(), liquid);
	}

	@Nullable
	public static IDrinkLiquid getLiquid(Fluid fluid) {
		return getLiquid(fluid.getName());
	}

	@Nullable
	public static IDrinkLiquid getLiquid(FluidStack fluid) {
		return getLiquid(fluid.getFluid());
	}
}
