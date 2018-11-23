package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

public class UniqueFluidStackSet extends FluidStackSet {
	@Override
	public boolean add(FluidStack e) {
		return e != null && this.getExisting(e) == null && this.fluidStacks.add(e.copy());
	}

	@Override
	public boolean remove(Object o) {
		if (this.contains(o)) {
			FluidStack r = (FluidStack) o;
			FluidStack existing = this.getExisting(r);
			this.fluidStacks.remove(existing);
		}
		return false;
	}
}
