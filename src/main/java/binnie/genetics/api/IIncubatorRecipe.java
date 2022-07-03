package binnie.genetics.api;

import binnie.core.machines.MachineUtil;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IIncubatorRecipe {
    boolean isInputLiquid(FluidStack liquid);

    boolean isInputLiquidSufficient(FluidStack liquid);

    @Deprecated
    boolean isItemStack(ItemStack stack);

    void doTask(MachineUtil machine);

    float getChance();

    boolean roomForOutput(MachineUtil machine);

    float getLossChance();

    @Nullable
    FluidStack getInput();

    @Nullable
    FluidStack getOutput();

    @Nullable
    ItemStack getInputStack();

    ItemStack getExpectedOutput();
}
