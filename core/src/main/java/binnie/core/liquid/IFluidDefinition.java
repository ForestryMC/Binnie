package binnie.core.liquid;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.features.IModFeature;

public interface IFluidDefinition extends IModFeature<FluidType> {

	default FluidType apply(FluidType fluid) {
		return fluid;
	}

	void setFluid(FluidType fluid);

	@Nullable
	FluidType getFluid();

	boolean hasFluid();

	default FluidType fluid() {
		FluidType block = getFluid();
		if (block == null) {
			throw new IllegalStateException("Called feature getter method before content creation.");
		}
		return block;
	}

	default FluidStack stack(int amount) {
		if (hasFluid()) {
			return new FluidStack(fluid().getFluid(), amount);
		}
		throw new IllegalStateException("This feature has no item to create a stack for.");
	}

	default FluidStack stack() {
		return stack(Fluid.BUCKET_VOLUME);
	}
}
