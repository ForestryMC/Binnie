package binnie.core.liquid;

import net.minecraftforge.fluids.FluidStack;

//ToDo: Why is this in the core mod and not in extra trees ?
public interface IDrinkLiquid {
	boolean isConsumable();

	int getColour();

	String getName();

	float getTransparency();

	String getIdentifier();

	float getABV();

	FluidStack get(int p0);
}
