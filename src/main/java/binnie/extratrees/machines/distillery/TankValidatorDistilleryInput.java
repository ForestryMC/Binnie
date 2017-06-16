package binnie.extratrees.machines.distillery;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;

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
