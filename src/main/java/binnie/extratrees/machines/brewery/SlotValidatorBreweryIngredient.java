package binnie.extratrees.machines.brewery;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;

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
