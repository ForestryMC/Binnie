package binnie.core.liquid;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {
	IIcon getIcon();

	void registerIcon(final IIconRegister register);

	String getName();

	String getIdentifier();

	FluidStack get(final int amount);

	int getColour();

	int getContainerColour();

	int getTransparency();

	boolean canPlaceIn(final FluidContainer container);

	boolean showInCreative(final FluidContainer container);
}
