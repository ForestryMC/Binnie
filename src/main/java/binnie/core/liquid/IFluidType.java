package binnie.core.liquid;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {
    IIcon getIcon();

    void registerIcon(IIconRegister register);

    String getName();

    String getIdentifier();

    FluidStack get(int amount);

    int getColor();

    int getContainerColor();

    int getTransparency();

    boolean canPlaceIn(FluidContainer container);

    boolean showInCreative(FluidContainer container);
}
