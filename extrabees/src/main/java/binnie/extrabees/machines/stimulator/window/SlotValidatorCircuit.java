package binnie.extrabees.machines.stimulator.window;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import forestry.api.circuits.ChipsetManager;
import net.minecraft.item.ItemStack;

public class SlotValidatorCircuit extends SlotValidator {
	public SlotValidatorCircuit() {
		super(ManagerMachine.getSpriteCircuit());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return !itemStack.isEmpty() && ChipsetManager.circuitRegistry.isChipset(itemStack);
	}

	@Override
	public String getTooltip() {
		return "Forestry Circuits";
	}
}
