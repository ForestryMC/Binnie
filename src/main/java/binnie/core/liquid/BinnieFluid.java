package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;

class BinnieFluid extends Fluid {
	private final String name;
	final IFluidType fluidType;

	@Override
	// TODO fix deprecated usage
	public String getLocalizedName() {
		return name;
	}

	public BinnieFluid(final IFluidType fluid) {
		super(fluid.getIdentifier());
		fluidType = fluid;
		name = fluid.getName();
	}

	@Override
	public int getColor() {
		return fluidType.getColour();
	}

	public IFluidType getType() {
		return fluidType;
	}
}
