package binnie.core.machines.component;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IComponentRecipe {
	boolean isRecipe();

	ItemStack doRecipe(final boolean takeItem);

	ItemStack getProduct();
}
