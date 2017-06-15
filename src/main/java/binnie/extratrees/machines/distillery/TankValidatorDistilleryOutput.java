package binnie.extratrees.machines.distillery;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;

public class TankValidatorDistilleryOutput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return DistilleryRecipes.isValidOutputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Distilled Liquids";
	}
}
