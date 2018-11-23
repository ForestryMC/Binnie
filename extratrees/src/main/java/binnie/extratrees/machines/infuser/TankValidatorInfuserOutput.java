package binnie.extratrees.machines.infuser;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;

public class TankValidatorInfuserOutput extends TankValidator {
	@Override
	public boolean isValid(FluidStack itemStack) {
		return InfuserRecipes.isValidOutputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Infused Liquids";
	}
}
