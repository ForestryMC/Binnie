package binnie.genetics.api;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IIncubatorRecipe {
    boolean isInputLiquid(final FluidStack p0);

    boolean isInputLiquidSufficient(final FluidStack p0);

    boolean isItemStack(final ItemStack p0);

    void doTask(final MachineUtil p0);

    float getChance();

    boolean roomForOutput(final MachineUtil p0);
}
