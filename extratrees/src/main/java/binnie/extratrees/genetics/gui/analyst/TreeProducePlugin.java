package binnie.extratrees.genetics.gui.analyst;

import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import binnie.genetics.api.IProducePlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public class TreeProducePlugin implements IProducePlugin {
	@Override
	public void getFluids(ItemStack inputStack, NonNullList<FluidStack> outputFluids) {
		FluidStack output = FruitPressRecipeManager.getOutput(inputStack);
		if (output != null) {
			outputFluids.add(output);
		}
	}

	@Override
	public void getFluids(FluidStack inputFluid, NonNullList<FluidStack> outputFluids) {
		BreweryRecipeManager.getOutput(inputFluid, outputFluids);
		for (int level = 0; level < 3; level++) {
			FluidStack output = DistilleryRecipeManager.getOutput(inputFluid, level);
			if (output != null) {
				outputFluids.add(output);
			}
		}
	}

	@Override
	public void getItems(ItemStack inputStack, NonNullList<ItemStack> outputItems) {

	}
}
