package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

public class UniqueFluidStackSet extends FluidStackSet {
	@Override
	public boolean add(final FluidStack e) {
		return e != null
			&& getExisting(e) == null
			&& itemStacks.add(e.copy());
	}

	@Override
	public boolean remove(final Object o) {
		if (contains(o)) {
			final FluidStack existing = getExisting((FluidStack) o);
			itemStacks.remove(existing);
		}
		return false;
	}
}
