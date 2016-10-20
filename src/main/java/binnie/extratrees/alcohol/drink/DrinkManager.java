package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class DrinkManager {
    static Map<String, IDrinkLiquid> drinkLiquids;

    public static IDrinkLiquid getLiquid(final String id) {
        return DrinkManager.drinkLiquids.get(id.toLowerCase());
    }

    public static void registerDrinkLiquid(final String id, final IDrinkLiquid liquid) {
        liquid.setIdent(id.toLowerCase());
        DrinkManager.drinkLiquids.put(id.toLowerCase(), liquid);
    }

    public static IDrinkLiquid getLiquid(final Fluid fluid) {
        return getLiquid(fluid.getName());
    }

    public static IDrinkLiquid getLiquid(final FluidStack fluid) {
        return (fluid != null) ? getLiquid(fluid.getFluid()) : null;
    }

    static {
        DrinkManager.drinkLiquids = new HashMap<String, IDrinkLiquid>();
    }
}
