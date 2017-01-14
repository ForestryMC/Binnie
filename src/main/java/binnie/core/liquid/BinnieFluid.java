package binnie.core.liquid;

import java.awt.Color;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

class BinnieFluid extends Fluid {
	private final String name;
	final IFluidType fluidType;

	@Override
	public String getLocalizedName(FluidStack stack) {
		return this.name;
	}

	public BinnieFluid(final IFluidType fluid) {
		super(fluid.getIdentifier(), fluid.getStill(), fluid.getFlowing());
		this.fluidType = fluid;
		this.name = fluid.getName();
	}

	@Override
	public int getColor() {
		//use color to set the alpha to 1.0F
		return new Color(this.fluidType.getColour()).getRGB();
	}

	public IFluidType getType() {
		return this.fluidType;
	}
}
