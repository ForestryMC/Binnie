package binnie.core.liquid;

import forestry.api.core.EnumContainerType;
import forestry.core.items.ItemFluidContainerForestry;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

public class FluidHandlerItemBinnie extends FluidHandlerItemStackSimple.Consumable {
	private final FluidContainerType containerType;

	public FluidHandlerItemBinnie(ItemStack container, FluidContainerType containerType) {
		super(container, Fluid.BUCKET_VOLUME);
		this.containerType = containerType;
	}

	private boolean contentsAllowed(FluidStack fluidStack) {
		if (fluidStack == null) {
			return false;
		}

		Fluid fluid = fluidStack.getFluid();
		if (fluid == null) {
			return false;
		}

		if(fluid instanceof BinnieFluid) {
			BinnieFluid binnieFluid = (BinnieFluid) fluid;
			return binnieFluid.getType().canPlaceIn(containerType);
		}
		return false;
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
