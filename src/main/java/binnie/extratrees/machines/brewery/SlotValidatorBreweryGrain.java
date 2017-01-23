package binnie.extratrees.machines.brewery;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;

public class SlotValidatorBreweryGrain extends SlotValidator {
	public SlotValidatorBreweryGrain() {
		super(SlotValidator.IconBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipes.isValidGrain(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Brewing Grains";
	}
}
