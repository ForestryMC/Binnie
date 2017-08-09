package binnie.extratrees.machines.lumbermill.window;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;

public class SlotValidatorLog extends SlotValidator {
	public SlotValidatorLog() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		ItemStack plank = LumbermillRecipeManager.getPlankProduct(itemStack);
		return !plank.isEmpty();
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.machine.lumbermill.tooltips.logs");
	}
}
