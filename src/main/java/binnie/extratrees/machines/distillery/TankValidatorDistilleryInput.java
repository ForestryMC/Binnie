package binnie.extratrees.machines.distillery;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

public class TankValidatorDistilleryInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return DistilleryRecipes.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Distillable Liquids";
	}
}
