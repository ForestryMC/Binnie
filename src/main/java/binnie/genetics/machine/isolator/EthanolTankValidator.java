package binnie.genetics.machine.isolator;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;
import net.minecraftforge.fluids.FluidStack;

public class EthanolTankValidator extends TankValidator {
    @Override
    public String getTooltip() {
        return I18N.localise("fluid.bioethanol");
    }

    @Override
    public boolean isValid(FluidStack liquid) {
        return liquid.getFluid().getName().equals("bioethanol");
    }
}
