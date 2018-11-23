package binnie.extratrees.machines.brewery.window;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;

public class TankValidatorFermentOutput extends TankValidator {
	@Override
	public boolean isValid(FluidStack itemStack) {
		return BreweryRecipeManager.isValidOutputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(WindowBrewery.LANG_KEY + ".tooltips.tank.fermented");
	}
}
