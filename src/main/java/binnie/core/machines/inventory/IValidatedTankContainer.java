package binnie.core.machines.inventory;

import net.minecraftforge.fluids.FluidStack;

public interface IValidatedTankContainer {
    boolean isTankReadOnly(int tank);

    boolean isLiquidValidForTank(FluidStack liquid, int tank);
}
