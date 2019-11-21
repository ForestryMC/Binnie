package binnie.genetics.machine;

import binnie.core.machines.inventory.TankValidator;
import forestry.core.fluids.Fluids;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class EthanolTankValidator extends TankValidator {
	@Override
	public String getTooltip() {
		return FluidRegistry.getFluidStack(Fluids.BIO_ETHANOL.getTag(), 1).getLocalizedName();
	}

	@Override
	public boolean isValid(final FluidStack stack) {
		return Fluids.BIO_ETHANOL.getTag().equals(stack.getFluid().getName());
	}
}
