package binnie.extratrees.alcohol.drink;

import net.minecraftforge.fluids.FluidStack;

public interface IDrinkLiquid {
    boolean isConsumable();

    int getColour();

    String getName();

    float getTransparency();

    String getIdentifier();

    void setIdent(final String p0);

    float getABV();

    FluidStack get(final int p0);
}
