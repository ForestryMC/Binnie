package binnie.extratrees.alcohol;

import net.minecraftforge.fluids.FluidStack;

public interface ICocktailLiquid extends ICocktailIngredient {
    FluidStack get(final int p0);
}
