package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

public class UniqueFluidStackSet extends FluidStackSet {
	@Override
	public boolean add(final FluidStack fluidStack) {
		return fluidStack != null
			&& getExisting(fluidStack) == null
			&& itemStacks.add(fluidStack.copy());
	}

	@Override
	public boolean remove(final Object object) {
		if (contains(object)) {
			final FluidStack existing = getExisting((FluidStack) object);
			itemStacks.remove(existing);
		}
		return false;
	}
}
