package binnie.extratrees.machines.fruitpress;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;

public class SlotValidatorSqueezable extends SlotValidator {
	public SlotValidatorSqueezable() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return FruitPressRecipes.isInput(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Fruit";
	}
}
