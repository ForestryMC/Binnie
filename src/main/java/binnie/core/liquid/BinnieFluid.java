// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;

class BinnieFluid extends Fluid
{
	private final String name;
	final IFluidType fluidType;

	@Override
	public String getLocalizedName() {
		return this.name;
	}

	public BinnieFluid(final IFluidType fluid) {
		super(fluid.getIdentifier());
		this.fluidType = fluid;
		this.name = fluid.getName();
	}

	@Override
	public int getColor() {
		return this.fluidType.getColour();
	}

	public IFluidType getType() {
		return this.fluidType;
	}
}
