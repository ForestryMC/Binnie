package binnie.extratrees.machines.fruitpress;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;

public class SlotValidatorSqueezable extends SlotValidator {
	public SlotValidatorSqueezable() {
		super(SlotValidator.IconBlock);
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
