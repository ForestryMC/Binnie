package binnie.extratrees.machines.infuser;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

public class TankValidatorInfuserOutput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return InfuserRecipes.isValidOutputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Infused Liquids";
	}
}
