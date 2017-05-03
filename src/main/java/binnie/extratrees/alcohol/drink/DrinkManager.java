package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class DrinkManager {
	protected static Map<String, IDrinkLiquid> drinkLiquids = new HashMap<>();

	public static IDrinkLiquid getLiquid(String id) {
		return DrinkManager.drinkLiquids.get(id.toLowerCase());
	}

	public static void registerDrinkLiquid(String id, IDrinkLiquid liquid) {
		liquid.setIdent(id.toLowerCase());
		DrinkManager.drinkLiquids.put(id.toLowerCase(), liquid);
	}

	public static IDrinkLiquid getLiquid(Fluid fluid) {
		return getLiquid(fluid.getName());
	}

	public static IDrinkLiquid getLiquid(FluidStack fluid) {
		if (fluid == null) {
			return null;
		}
		return getLiquid(fluid.getFluid());
	}
}
