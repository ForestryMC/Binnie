package binnie.extrabees.machines.mutator.window;

import net.minecraft.item.ItemStack;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.utils.AlvearyMutationHandler;

public class SlotValidatorMutator extends SlotValidator {
	public SlotValidatorMutator() {
		super(ManagerMachine.getSpriteBee());
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return AlvearyMutationHandler.isMutationItem(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Mutagenic Agents";
	}
}
