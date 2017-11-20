package binnie.extratrees.machines.distillery.window;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;

public class TankValidatorDistilleryInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return DistilleryRecipeManager.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.distillery.tooltips.tank.distillable");
	}
}
