package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.awt.Color;

class BinnieFluid extends Fluid {
	private final FluidType fluidType;
	private final int color;

	BinnieFluid(FluidType fluid) {
		super(fluid.getIdentifier(), fluid.getStill(), fluid.getFlowing());
		this.fluidType = fluid;
		this.color = new Color(this.fluidType.getColor()).getRGB();
	}

	@Override
	public String getLocalizedName(FluidStack stack) {
		return fluidType.getDisplayName();
	}

	@Override
	public int getColor() {
		return color;
	}

	public FluidType getType() {
		return this.fluidType;
	}
}
