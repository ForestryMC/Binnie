package binnie.extratrees.machines.brewery.window;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import net.minecraft.item.ItemStack;

public class SlotValidatorBreweryGrain extends SlotValidator {
	public SlotValidatorBreweryGrain() {
		super(ManagerMachine.getSpriteBlock());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return BreweryRecipeManager.isValidGrain(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(WindowBrewery.LANG_KEY + ".tooltips.slot.grain");
	}
}
