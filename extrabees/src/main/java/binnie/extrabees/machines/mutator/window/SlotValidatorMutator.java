package binnie.extrabees.machines.mutator.window;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.utils.AlvearyMutationHandler;
import net.minecraft.item.ItemStack;

public class SlotValidatorMutator extends SlotValidator {
	public SlotValidatorMutator() {
		super(ManagerMachine.getSpriteBee());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return AlvearyMutationHandler.isMutationItem(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Mutagenic Agents";
	}
}
