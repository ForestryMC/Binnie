package binnie.genetics.api;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IIncubatorRecipe {
	boolean isInputLiquid(final FluidStack p0);

	boolean isInputLiquidSufficient(final FluidStack p0);

	void doTask(final MachineUtil p0);

	float getChance();

	float getLossChance();

	boolean roomForOutput(final MachineUtil p0);

	FluidStack getInput();

	FluidStack getOutput();

	ItemStack getInputStack();

	@Nullable
	ItemStack getExpectedOutput();
}
