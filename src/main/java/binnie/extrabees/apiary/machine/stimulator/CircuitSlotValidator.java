package binnie.extrabees.apiary.machine.stimulator;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.circuits.ChipsetManager;
import net.minecraft.item.ItemStack;

public class CircuitSlotValidator extends SlotValidator {
	public CircuitSlotValidator() {
		super(SlotValidator.IconCircuit);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return itemStack != null && ChipsetManager.circuitRegistry.isChipset(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(ExtraBees.instance, "machine.alveay.stimulator.tooltip");
	}
}
