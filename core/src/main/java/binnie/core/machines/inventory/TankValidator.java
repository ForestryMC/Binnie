package binnie.core.machines.inventory;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;

public abstract class TankValidator extends Validator<FluidStack> {
	@Override
	public abstract boolean isValid(final FluidStack p0);

	public static class Basic extends TankValidator {
		private final Fluid fluid;

		public Basic(final String name) {
			this.fluid = Binnie.LIQUID.getFluidStack(name, 1).getFluid();
		}

		@Override
		public boolean isValid(final FluidStack stack) {
			return new FluidStack(this.fluid, 1).isFluidEqual(stack);
		}

		@Override
		public String getTooltip() {
			return new FluidStack(this.fluid, 1).getLocalizedName();
		}
	}
}
