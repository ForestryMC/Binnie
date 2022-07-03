package binnie.core.machines.inventory;

import binnie.Binnie;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class TankValidator extends Validator<FluidStack> {
    @Override
    public abstract boolean isValid(FluidStack liquid);

    public static class Basic extends TankValidator {
        private Fluid fluid;

        public Basic(String name) {
            fluid = Binnie.Liquid.getLiquidStack(name, 1).getFluid();
        }

        @Override
        public boolean isValid(FluidStack liquid) {
            return new FluidStack(fluid, 1).isFluidEqual(liquid);
        }

        @Override
        public String getTooltip() {
            return new FluidStack(fluid, 1).getLocalizedName();
        }
    }
}
