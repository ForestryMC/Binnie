package binnie.genetics.machine.polymeriser;

import binnie.core.machines.inventory.Validator;
import binnie.core.util.I18N;
import binnie.genetics.item.GeneticLiquid;
import net.minecraftforge.fluids.FluidStack;

class DnaValidator extends Validator<FluidStack> {
    @Override
    public boolean isValid(FluidStack itemStack) {
        return GeneticLiquid.RawDNA.get(1).isFluidEqual(itemStack);
    }

    @Override
    public String getTooltip() {
        return I18N.localise("fluid.binnie.dna.raw");
    }
}
