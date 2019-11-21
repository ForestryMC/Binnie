package binnie.extratrees.machines.brewery.window;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import net.minecraftforge.fluids.FluidStack;

public class TankValidatorFermentInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return BreweryRecipeManager.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(WindowBrewery.LANG_KEY + ".tooltips.tank.fermentable");
	}
}
