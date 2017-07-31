package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;

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
		return I18N.localise("extratrees.machine.machine.brewery.tooltips.slot.ingredient");
	}
}
