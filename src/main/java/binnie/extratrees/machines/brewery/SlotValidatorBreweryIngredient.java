package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;

public class SlotValidatorBreweryIngredient extends SlotValidator {
	public SlotValidatorBreweryIngredient() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipes.isValidIngredient(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Brewing Ingredient";
	}
}
