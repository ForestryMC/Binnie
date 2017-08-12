package binnie.core.liquid;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {

	ResourceLocation getFlowing();

	ResourceLocation getStill();

	String getDisplayName();

	String getIdentifier();

	FluidStack get(int amount);

	default FluidStack get(){
		return get(Fluid.BUCKET_VOLUME);
	}

	int getColor();

	int getContainerColor();

	int getTransparency();

	boolean canPlaceIn(FluidContainerType type);

	boolean showInCreative(FluidContainerType type);
}
