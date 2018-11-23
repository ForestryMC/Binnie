package binnie.genetics.api.acclimatiser;

import net.minecraft.item.ItemStack;

public interface IToleranceType {
	float getEffect(ItemStack stack);

	default boolean hasEffect(ItemStack stack) {
		return this.getEffect(stack) != 0.0f;
	}
}
