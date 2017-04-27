package binnie.genetics.api;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IIncubatorRecipe {
	boolean isInputLiquid(FluidStack liquid);

	boolean isInputLiquidSufficient(FluidStack liquid);

	boolean isItemStack(ItemStack stack);

	void doTask(MachineUtil machine);

	float getChance();

	boolean roomForOutput(MachineUtil machine);
}
