package binnie.core.liquid;

import forestry.api.core.IFluidContainerPermission;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class BinnieContainerPermission implements IFluidContainerPermission {

	private final FluidContainer container;
	
	public BinnieContainerPermission(FluidContainer container) {
		this.container = container;
	}
	
	@Override
	public boolean contentsAllowed(FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		if(fluid instanceof BinnieFluid){
			BinnieFluid binnie = (BinnieFluid) fluid;
			return binnie.fluidType.canPlaceIn(container);
		}
		return true;
	}
	
}
