package binnie.extratrees.machines.infuser;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

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
