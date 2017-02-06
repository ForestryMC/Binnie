package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class DrinkManager {
	static Map<String, IDrinkLiquid> drinkLiquids = new HashMap<>();

	public static IDrinkLiquid getLiquid(final String id) {
		return DrinkManager.drinkLiquids.get(id.toLowerCase());
	}

	public static void registerDrinkLiquid(final IDrinkLiquid liquid) {
		DrinkManager.drinkLiquids.put(liquid.getIdentifier(), liquid);
	}

	public static IDrinkLiquid getLiquid(final Fluid fluid) {
		return getLiquid(fluid.getName());
	}

	public static IDrinkLiquid getLiquid(final FluidStack fluid) {
		return (fluid != null) ? getLiquid(fluid.getFluid()) : null;
	}

}
