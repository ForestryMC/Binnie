package binnie.extrabees.machines.mutator;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.machines.ExtraBeeMachines;
import binnie.extrabees.machines.mutator.window.SlotValidatorMutator;

public class AlvearyMutator extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public static final int SLOT_MUTATOR = 0;

	public AlvearyMutator() {
		super("mutator");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraBeeMachines.ComponentExtraBeeGUI(machine, ExtraBeesGUID.ALVEARY_MUTATOR);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(AlvearyMutator.SLOT_MUTATOR, getSlotRL("mutator")).setValidator(new SlotValidatorMutator());
		new ComponentMutatorModifier(machine);
	}

}
