package binnie.extratrees.alcohol;

import net.minecraftforge.fluids.FluidStack;

public interface ICocktailLiquid extends ICocktailIngredient {
	/**
	 * @return Returns a {@link FluidStack} of this fluid with a specific amount.
	 */
	FluidStack get(int amount);
}
