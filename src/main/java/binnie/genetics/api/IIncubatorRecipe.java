package binnie.genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.MachineUtil;

public interface IIncubatorRecipe {
	boolean isInputLiquid(@Nullable final FluidStack fluid);

	boolean isInputLiquidSufficient(@Nullable final FluidStack fluid);

	void doTask(final MachineUtil p0);

	float getChance();

	float getLossChance();

	boolean roomForOutput(final MachineUtil p0);

	FluidStack getInput();

	@Nullable
	FluidStack getOutput();

	ItemStack getInputStack();

	ItemStack getExpectedOutput();
}
