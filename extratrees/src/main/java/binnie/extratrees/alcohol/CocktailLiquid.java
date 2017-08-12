package binnie.extratrees.alcohol;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.liquid.FluidType;
import binnie.core.util.I18N;

public class CocktailLiquid implements ICocktailLiquid {
	private FluidType definition;
	private float abv;

	public CocktailLiquid(FluidType definition, float abv) {
		this.definition = definition;
		this.abv = abv;
	}

	@Override
	public FluidStack get(int amount) {
		return definition.get(amount);
	}

	@Override
	public String getDisplayName() {
		return definition.getDisplayName();
	}

	@Override
	public String getIdentifier() {
		return definition.getIdentifier();
	}

	@Override
	public int getColor() {
		return definition.getColor();
	}

	@Override
	public int getTransparency() {
		return definition.getTransparency();
	}

	@Override
	public String getTooltip(int ratio) {
		String unlocalized = "extratrees.cocktail.liquid.tooltip";
		if(ratio > 1){
			unlocalized = "extratrees.cocktail.liquids.tooltip";
		}
		return I18N.localise(unlocalized, ratio, definition.getDisplayName());
	}

	@Override
	public float getABV() {
		return abv;
	}
}
