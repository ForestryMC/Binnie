package binnie.extratrees.machines.brewery.window;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;

public class SlotValidatorBreweryYeast extends SlotValidator {
	public SlotValidatorBreweryYeast() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipeManager.isValidYeast(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(WindowBrewery.LANG_KEY + ".tooltips.slot.yeast");
	}
}
