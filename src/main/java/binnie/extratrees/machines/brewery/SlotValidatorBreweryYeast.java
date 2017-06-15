package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;

public class SlotValidatorBreweryYeast extends SlotValidator {
	public SlotValidatorBreweryYeast() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipes.isValidYeast(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Yeast";
	}
}
