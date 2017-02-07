package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DrinkManager {
	static Map<String, IDrinkLiquid> drinkLiquids = new HashMap<>();

	@Nullable
	public static IDrinkLiquid getLiquid(final String id) {
		return DrinkManager.drinkLiquids.get(id.toLowerCase());
	}

	public static void registerDrinkLiquid(final IDrinkLiquid liquid) {
		DrinkManager.drinkLiquids.put(liquid.getIdentifier(), liquid);
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
