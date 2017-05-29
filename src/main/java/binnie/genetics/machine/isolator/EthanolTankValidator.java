package binnie.genetics.machine.isolator;

import binnie.core.machines.inventory.TankValidator;
import net.minecraftforge.fluids.FluidStack;

public class EthanolTankValidator extends TankValidator {
	@Override
	public String getTooltip() {
		return "Ethanol";
	}

	@Override
	public boolean isValid(FluidStack liquid) {
		return liquid.getFluid().getName().equals("bioethanol");
	}
}
