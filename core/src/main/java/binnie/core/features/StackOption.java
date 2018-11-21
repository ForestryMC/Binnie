package binnie.core.features;

import java.util.function.Consumer;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public enum StackOption implements Consumer<ItemStack> {
	MAX_COUNT {
		@Override
		public void accept(ItemStack stack) {
			int maxCount = stack.isStackable() ? 64 : 1;
			stack.setCount(maxCount);
		}
	},
	WILDCARD {
		@Override
		public void accept(ItemStack stack) {
			stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
		}
	},
	MAX_DAMAGE {
		@Override
		public void accept(ItemStack stack) {
			stack.setItemDamage(stack.getMaxDamage());
		}
	}
}
