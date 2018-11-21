package binnie.core.features;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import binnie.core.item.IItemSubtype;

public interface IFeatureItem<I extends Item> {

	String getIdentifier();

	boolean hasItem();

	I item();

	default ItemStack stack() {
		return stack(1);
	}

	default ItemStack stack(int amount) {
		return stack(amount, 0);
	}

	default ItemStack stack(IItemSubtype type) {
		return stack(1, type);
	}

	default ItemStack stack(int amount, IItemSubtype type) {
		return stack(amount, type.ordinal());
	}

	default ItemStack stack(int amount, int metadata) {
		if (hasItem()) {
			return new ItemStack(item(), amount, metadata);
		}
		throw new IllegalStateException("This feature has no item to create a stack for.");
	}

	default ItemStack stack(StackOption... options) {
		ItemStack stack = stack();
		for (StackOption option : options) {
			option.accept(stack);
		}
		return stack;
	}
}
