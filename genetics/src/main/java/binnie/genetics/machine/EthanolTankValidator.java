package binnie.genetics.machine;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import forestry.core.fluids.Fluids;

import binnie.core.machines.inventory.TankValidator;

public class EthanolTankValidator extends TankValidator {
	@Override
	public String getTooltip() {
		return FluidRegistry.getFluidStack(Fluids.BIO_ETHANOL.getTag(), 1).getLocalizedName();
	}

	@Override
	public boolean isValid(FluidStack stack) {
		return Fluids.BIO_ETHANOL.getTag().equals(stack.getFluid().getName());
	}
}
