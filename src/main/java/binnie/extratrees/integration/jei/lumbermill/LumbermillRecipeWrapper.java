package binnie.extratrees.integration.jei.lumbermill;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.machines.lumbermill.LumbermillLogic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public class LumbermillRecipeWrapper extends BlankRecipeWrapper {
	private static final FluidStack WATER = new FluidStack(FluidRegistry.WATER, LumbermillLogic.WATER_PER_TICK * LumbermillLogic.PROCESS_LENGTH);

	private final ItemStack inputLog;
	private final ItemStack outputPlanks;

	public LumbermillRecipeWrapper(ItemStack inputLog, ItemStack outputPlanks) {
		this.inputLog = inputLog;
		this.outputPlanks = outputPlanks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, inputLog);
		ingredients.setInput(FluidStack.class, WATER);

		ExtraTrees.items();
		ExtraTrees.items();
		ingredients.setOutputs(ItemStack.class, Arrays.asList(
				ExtraTreeItems.Bark.get(1),
				outputPlanks,
				ExtraTreeItems.Sawdust.get(1)
		));
	}
}
