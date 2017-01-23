package binnie.extratrees.machines.brewery;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

public class TankValidatorFermentInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return BreweryRecipes.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Fermentable Liquids";
	}
}
