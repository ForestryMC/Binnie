package binnie.extratrees.integration.crafttweaker;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;

public class CraftTweakerUtil {

	public static ItemStack getItemStack(IIngredient item) {
		if (item == null) {
			return ItemStack.EMPTY;
		}

		Object internal = item.getInternal();
		if (!(internal instanceof ItemStack)) {
			CraftTweakerAPI.logError("Not a valid item stack: " + item);
			return ItemStack.EMPTY;
		}
		return (ItemStack) internal;
	}

	public static FluidStack getLiquidStack(IIngredient stack) {
		if (stack == null || !(stack.getInternal() instanceof FluidStack)) {
			return null;
		}

		return (FluidStack) stack.getInternal();
	}
}
