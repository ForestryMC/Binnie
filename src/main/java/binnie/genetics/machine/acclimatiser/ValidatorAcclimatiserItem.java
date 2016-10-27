package binnie.genetics.machine.acclimatiser;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;

public class ValidatorAcclimatiserItem extends SlotValidator {
	public ValidatorAcclimatiserItem() {
		super(null);
	}

	@Override
	public boolean isValid(final ItemStack stack) {
		for (final ToleranceType type : ToleranceType.values()) {
			if (type.hasEffect(stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTooltip() {
		return "Acclimatising Items";
	}
}
