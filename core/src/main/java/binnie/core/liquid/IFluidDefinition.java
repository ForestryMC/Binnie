package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidDefinition {

	FluidStack get(int amount);

	default FluidStack get() {
		return get(Fluid.BUCKET_VOLUME);
	}

	FluidType getType();
}
