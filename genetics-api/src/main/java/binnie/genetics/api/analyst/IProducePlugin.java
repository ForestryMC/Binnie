package binnie.genetics.api.analyst;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public interface IProducePlugin {
	void getFluids(ItemStack inputStack, NonNullList<FluidStack> outputFluids);
	void getFluids(FluidStack inputFluid, NonNullList<FluidStack> outputFluids);
	void getItems(ItemStack inputStack, NonNullList<ItemStack> outputItems);
}
