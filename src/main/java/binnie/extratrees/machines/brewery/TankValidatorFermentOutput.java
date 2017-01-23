package binnie.extratrees.machines.brewery;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

public class TankValidatorFermentOutput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return BreweryRecipes.isValidOutputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Fermented Liquids";
	}
}
