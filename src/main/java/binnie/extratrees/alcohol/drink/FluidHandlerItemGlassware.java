package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.Glassware;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

// TODO implement cocktails and only allow glassware to hold the correct cocktails.
public class FluidHandlerItemGlassware extends FluidHandlerItemStackSimple {

	public FluidHandlerItemGlassware(ItemStack container, Glassware glassware) {
		super(container, glassware.getCapacity());
	}

	private boolean contentsAllowed(FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		return fluid != null && DrinkManager.getLiquid(fluidStack) != null;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return contentsAllowed(fluid);
	}

	@Override
	public boolean canDrainFluidType(FluidStack fluid) {
		return contentsAllowed(fluid);
	}
}
