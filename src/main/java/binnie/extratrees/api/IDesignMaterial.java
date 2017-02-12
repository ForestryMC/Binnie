package binnie.extratrees.api;

import net.minecraft.item.ItemStack;

public interface IDesignMaterial {
	ItemStack getStack();

	ItemStack getStack(boolean fireproof);

	String getName();

	int getColour();
}
