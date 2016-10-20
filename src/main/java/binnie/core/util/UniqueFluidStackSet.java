package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

public class UniqueFluidStackSet extends FluidStackSet {
    @Override
    public boolean add(final FluidStack e) {
        return e != null && this.getExisting(e) == null && this.itemStacks.add(e.copy());
    }

    @Override
    public boolean remove(final Object o) {
        if (this.contains(o)) {
            final FluidStack r = (FluidStack) o;
            final FluidStack existing = this.getExisting(r);
            this.itemStacks.remove(existing);
        }
        return false;
    }
}
