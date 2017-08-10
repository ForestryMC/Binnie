package binnie.core.liquid;

import net.minecraftforge.fluids.FluidStack;

public interface IDrinkLiquid {
	boolean isConsumable();

	int getColour();

	String getName();

	float getTransparency();

	String getIdentifier();

	float getABV();

	FluidStack get(final int p0);
}
