package binnie.genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.MachineUtil;

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
