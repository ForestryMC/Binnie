package binnie.genetics.machine;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.Validator;

public class SlotValidatorInoculatorCatalyst extends Validator<ItemStack> {
	@Override
	public boolean isValid(final ItemStack itemStack) {
		return false;
	}

	@Override
	public String getTooltip() {
		return "Inoculation Catalyst";
	}
}
