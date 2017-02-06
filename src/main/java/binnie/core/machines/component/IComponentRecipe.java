package binnie.core.machines.component;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IComponentRecipe {
	boolean isRecipe();

	@Nullable
	ItemStack doRecipe(final boolean takeItem);

	@Nullable
	ItemStack getProduct();
}
