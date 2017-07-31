package binnie.extratrees.machines.distillery;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;

public class TankValidatorDistilleryInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return DistilleryRecipes.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.machine.distillery.tooltips.tank.distillable");
	}
}
