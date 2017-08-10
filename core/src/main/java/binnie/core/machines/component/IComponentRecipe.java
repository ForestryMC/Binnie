package binnie.core.machines.component;

import net.minecraft.item.ItemStack;

public interface IComponentRecipe {
	boolean isRecipe();

	ItemStack doRecipe(final boolean takeItem);

	ItemStack getProduct();
}
