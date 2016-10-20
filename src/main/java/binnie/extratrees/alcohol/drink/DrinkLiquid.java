package binnie.extratrees.alcohol.drink;

import binnie.Binnie;
import net.minecraftforge.fluids.FluidStack;

public class DrinkLiquid implements IDrinkLiquid {
    String name;
    int colour;
    float transparency;
    float abv;
    public String ident;

    public DrinkLiquid(final String name, final int colour, final float transparency, final float abv) {
        this.name = name;
        this.colour = colour;
        this.transparency = transparency;
        this.abv = abv;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public int getColour() {
        return this.colour;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getTransparency() {
        return this.transparency;
    }

    @Override
    public String getIdentifier() {
        return this.ident;
    }

    @Override
    public void setIdent(final String lowerCase) {
        this.ident = lowerCase;
    }

    @Override
    public float getABV() {
        return this.abv;
    }

    @Override
    public FluidStack get(final int amount) {
        return Binnie.Liquid.getLiquidStack(this.ident, amount);
    }
}
