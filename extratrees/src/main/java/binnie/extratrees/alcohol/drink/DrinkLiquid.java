package binnie.extratrees.alcohol.drink;

import binnie.core.Binnie;
import binnie.core.liquid.IDrinkLiquid;
import net.minecraftforge.fluids.FluidStack;

public class DrinkLiquid implements IDrinkLiquid {
	private final String name;
	private final int colour;
	private final float transparency;
	private final float abv;
	private final String ident;

	public DrinkLiquid(final String name, final int colour, final float transparency, final float abv, final String ident) {
		this.name = name;
		this.colour = colour;
		this.transparency = transparency;
		this.abv = abv;
		this.ident = ident;
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
	public float getABV() {
		return this.abv;
	}

	@Override
	public FluidStack get(final int amount) {
		return Binnie.LIQUID.getFluidStack(this.ident, amount);
	}
}
