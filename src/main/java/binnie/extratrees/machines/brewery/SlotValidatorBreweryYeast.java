package binnie.extratrees.machines.brewery;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;

public class SlotValidatorBreweryYeast extends SlotValidator {
	public SlotValidatorBreweryYeast() {
		super(SlotValidator.IconBlock);
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
