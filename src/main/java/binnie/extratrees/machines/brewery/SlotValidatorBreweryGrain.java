package binnie.extratrees.machines.brewery;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;

public class SlotValidatorBreweryGrain extends SlotValidator {
	public SlotValidatorBreweryGrain() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipes.isValidGrain(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.machine.brewery.tooltips.slot.grain");
	}
}
