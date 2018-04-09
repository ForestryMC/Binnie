package binnie.core.liquid;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nullable;

public class FluidHandlerItemBinnie extends FluidHandlerItemStackSimple {
	private final FluidContainerType containerType;

	FluidHandlerItemBinnie(ItemStack container, FluidContainerType containerType) {
		super(container, Fluid.BUCKET_VOLUME);
		this.containerType = containerType;
	}

	private boolean contentsAllowed(@Nullable FluidStack fluidStack) {
		if (fluidStack == null) {
			return false;
		}

		final Fluid fluid = fluidStack.getFluid();
		if (fluid == null) {
			return false;
		}

		if (fluid instanceof BinnieFluid) {
			final BinnieFluid binnieFluid = (BinnieFluid) fluid;
			final FluidType definition = binnieFluid.getType();
			return definition.canPlaceIn(containerType);
		}
		return false;
	}

	@Override
	public boolean canFillFluidType(@Nullable FluidStack fluid) {
		return contentsAllowed(fluid);
	}

	@Override
	public boolean canDrainFluidType(@Nullable FluidStack fluid) {
		return contentsAllowed(fluid);
	}

	@Override
	protected void setContainerToEmpty() {
		super.setContainerToEmpty();
		container.setItemDamage(0); // show the empty container model
	}

	@Override
	protected void setFluid(FluidStack fluid) {
		super.setFluid(fluid);
		container.setItemDamage(1); // show the filled container model
	}
}
