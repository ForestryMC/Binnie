package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;

class BinnieFluid extends Fluid {
	protected IFluidType fluidType;

	private String name;

	@Override
	// TODO fix deprecated usage
	public String getLocalizedName() {
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
