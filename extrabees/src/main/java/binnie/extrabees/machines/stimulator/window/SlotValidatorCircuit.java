package binnie.extrabees.machines.stimulator.window;

import net.minecraft.item.ItemStack;

import forestry.api.circuits.ChipsetManager;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;

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
