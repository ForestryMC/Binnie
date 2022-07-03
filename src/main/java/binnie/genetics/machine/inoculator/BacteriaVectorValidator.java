package binnie.genetics.machine.inoculator;

import binnie.core.machines.inventory.Validator;
import binnie.genetics.item.GeneticLiquid;
import net.minecraftforge.fluids.FluidStack;

public class BacteriaVectorValidator extends Validator<FluidStack> {
    @Override
    public boolean isValid(FluidStack object) {
        return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
    }

    @Override
    public String getTooltip() {
        return GeneticLiquid.BacteriaVector.getName();
    }
}
