package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.FluidStack;

public interface IDrinkLiquid {
	boolean isConsumable();

	int getColour();

	String getName();

	float getTransparency();

	String getIdentifier();

	void setIdent(String lowerCase);

	float getABV();

	FluidStack get(int amount);
}
