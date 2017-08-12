package binnie.core.liquid;

import java.awt.Color;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

class BinnieFluid extends Fluid {
	private final String name;
	private final FluidDefinition fluidDefinition;
	private final int color;

	public BinnieFluid(FluidDefinition fluid) {
		super(fluid.getIdentifier(), fluid.getStill(), fluid.getFlowing());
		this.fluidDefinition = fluid;
		this.name = fluid.getDisplayName();
		this.color = new Color(this.fluidDefinition.getColor()).getRGB();
	}

	@Override
	public String getLocalizedName(FluidStack stack) {
		return this.name;
	}

	@Override
	public int getColor() {
		return color;
	}

	public FluidDefinition getDefinition() {
		return this.fluidDefinition;
	}
}
