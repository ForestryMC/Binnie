package binnie.genetics.machine.incubator;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IIncubatorRecipe {
	boolean isInputLiquid(@Nullable FluidStack fluid);

	boolean isInputLiquidSufficient(@Nullable FluidStack fluid);

	void doTask(MachineUtil machine);

	float getChance();

	float getLossChance();

	boolean roomForOutput(MachineUtil machine);

	FluidStack getInput();

	@Nullable
	FluidStack getOutput();

	ItemStack getInputStack();

	ItemStack getExpectedOutput();
}
