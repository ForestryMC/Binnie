package binnie.extratrees.alcohol.drink;

import binnie.Binnie;
import net.minecraftforge.fluids.FluidStack;

public class DrinkLiquid implements IDrinkLiquid {
	public String ident;

	protected String name;
	protected int colour;
	protected float transparency;
	protected float abv;

	public DrinkLiquid(String name, int colour, float transparency, float abv) {
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
		return colour;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getTransparency() {
		return transparency;
	}

	@Override
	public String getIdentifier() {
		return ident;
	}

	@Override
	public void setIdent(String lowerCase) {
		ident = lowerCase;
	}

	@Override
	public float getABV() {
		return abv;
	}

	@Override
	public FluidStack get(int amount) {
		return Binnie.Liquid.getLiquidStack(ident, amount);
	}
}
