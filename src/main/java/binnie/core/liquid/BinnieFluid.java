package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

class BinnieFluid extends Fluid {
    protected IFluidType fluidType;

    private String name;

    @Override
    public String getLocalizedName(FluidStack stack) {
        return name;
    }

    public BinnieFluid(IFluidType fluid) {
        super(fluid.getIdentifier());
        fluidType = fluid;
        name = fluid.getName();
    }

    @Override
    public int getColor() {
        return fluidType.getColor();
    }

    public IFluidType getType() {
        return fluidType;
    }
}
