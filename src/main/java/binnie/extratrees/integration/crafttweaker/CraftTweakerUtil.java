package binnie.extratrees.integration.crafttweaker;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;

public class CraftTweakerUtil {

	public static ItemStack getItemStack(IIngredient item) {
		if(item == null)
			return ItemStack.EMPTY;

		Object internal = item.getInternal();
		if(internal == null || !(internal instanceof ItemStack)) {
			MineTweakerAPI.logError("Not a valid item stack: " + item);
		}
		return (ItemStack) internal;
	}

	public static FluidStack getLiquidStack(IIngredient stack) {
		if(stack == null || !(stack.getInternal() instanceof FluidStack))
			return null;

		return (FluidStack) stack.getInternal();
	}
}
