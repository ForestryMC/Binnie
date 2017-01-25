package binnie.core.liquid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {

	ResourceLocation getFlowing();

	ResourceLocation getStill();

	String getDisplayName();

	String getIdentifier();

	FluidStack get(final int p0);

	int getColour();

	int getContainerColour();

	int getTransparency();

	boolean canPlaceIn(final FluidContainerType p0);

	boolean showInCreative(final FluidContainerType p0);
}
