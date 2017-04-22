package binnie.core.liquid;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;

public interface IFluidType {
	IIcon getIcon();

	void registerIcon(IIconRegister register);

	String getName();

	String getIdentifier();

	FluidStack get(int amount);

	int getColour();

	int getContainerColour();

	int getTransparency();

	boolean canPlaceIn(FluidContainer container);

	boolean showInCreative(FluidContainer container);
}
