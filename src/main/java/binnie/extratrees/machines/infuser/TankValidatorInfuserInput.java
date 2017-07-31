package binnie.extratrees.machines.infuser;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;

public class TankValidatorInfuserInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return InfuserRecipes.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Infusable Liquids";
	}
}
